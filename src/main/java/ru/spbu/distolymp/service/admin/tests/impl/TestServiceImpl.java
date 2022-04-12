package ru.spbu.distolymp.service.admin.tests.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.files.FileNameGenerator;
import ru.spbu.distolymp.common.files.FileUtils;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionType;
import ru.spbu.distolymp.common.tasks.filegenerator.TestFileGenerator;
import ru.spbu.distolymp.common.tasks.parser.TestParser;
import ru.spbu.distolymp.dto.admin.tests.*;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.tests.api.TestListMapper;
import ru.spbu.distolymp.mapper.admin.tests.api.TestPreviewMapper;
import ru.spbu.distolymp.mapper.admin.tests.api.TestViewMapper;
import ru.spbu.distolymp.mapper.entity.tasks.api.TestMapper;
import ru.spbu.distolymp.repository.tasks.TestRepository;
import ru.spbu.distolymp.service.admin.tests.api.TestService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.tasks.TestCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.TestSpecsConverter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TestServiceImpl extends TestCrudServiceImpl implements TestService {
    private final TestPreviewMapper testPreviewMapper;
    private final TestViewMapper testViewMapper;
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String TESTS_PARAM = "testList";
    private static final String QUESTION_NUMBER_PARAM = "questionNumber";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public TestServiceImpl(TestRepository testRepository,
                           TestListMapper testListMapper,
                           ListingProblemCrudService listingProblemCrudService,
                           TestMapper testMapper,
                           TestPreviewMapper testPreviewMapper,
                           TestViewMapper testViewMapper) {
        super(testRepository, testListMapper, listingProblemCrudService, testMapper);
        this.testPreviewMapper = testPreviewMapper;
        this.testViewMapper = testViewMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTestModelMap(ModelMap modelMap) {
        List<TestListDto> testDtoList = getTests(SORT_BY_ID_DESC);
        modelMap.put(TESTS_PARAM, testDtoList);
        modelMap.put("testForCopy", new TestListDto());
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTestModelMap(ModelMap modelMap, TestFilter testFilter) {
        Specification<Test> specs = TestSpecsConverter.toSpecs(testFilter);
        List<TestListDto> testDtoList;
        if (specs == null)
            testDtoList = getTests(SORT_BY_ID_DESC);
        else
            testDtoList = getTests(SORT_BY_ID_DESC, specs);
        modelMap.put(TESTS_PARAM, testDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, ModelMap modelMap) {
        Test test = getTestById(id).orElseThrow(ResourceNotFoundException::new);
        String fileName = test.getTestFolder() + "/" + test.getParFileName();
        byte[] file = fileService.getFileWithName(fileName);
        String fileContent = new String(file, CHARSET);
        TestDto testDto = testMapper.toDto(test, fileContent);

        modelMap.put("test", testDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAddPageModelMap(ModelMap modelMap) {
        TestDto testDto = new TestDto();
        testDto.setWidth(0);
        testDto.setHeight(0);
        testDto.setRandomOrder(true);
        testDto.setQuestionsNumber(new int[] {0, 0, 0});
        testDto.setAllQuestionsNumber(new int[] {0, 0, 0});
        testDto.setQuestionSkip(true);
        testDto.setQuestionList(new ArrayList<>());

        modelMap.put("test", testDto);
    }

    @Override
    @Transactional
    public void updateTest(TestDto testDto) {
        Test test = testMapper.toEntity(testDto);

        initFields(test);

        MultipartFile image = testDto.getImage();
        MultipartFile paramFile = testDto.getParamFile();
        String oldImageName = test.getImageFileName();
        String oldParamFileName = test.getParFileName();

        boolean newImageUpload = !"".equals(image.getOriginalFilename());
        boolean newParamFileUpload = !"".equals(paramFile.getOriginalFilename());
        boolean oldImageExists = (oldImageName != null) && (!"".equals(oldImageName));
        boolean oldParamFileExists = (oldParamFileName != null) && (!"".equals(oldParamFileName));
        boolean sameParamFileName = false;
        boolean deleteImage = testDto.isDeleteImage();

        Map<String, byte[]> filesWithNames = new HashMap<>();

        if (newParamFileUpload) {
            if (testDto.getId() == null) {
                String dirName = FileNameGenerator.generateFileName("");
                test.setTestFolder(dirName);
            }
            String fileName = paramFile.getOriginalFilename();
            String testFileName = fileName;
            int index = fileName.lastIndexOf("_param.php");
            if (index != -1) {
                testFileName = fileName.substring(0, index) + ".php";
            } else {
                fileName = fileName.substring(0, fileName.length() - 4) + "_param.php";
            }

            test.setParFileName(fileName);
            test.setBrcFileName(testFileName);

            if (fileName.equals(testDto.getParamFileName())) sameParamFileName = true;

            fileName = test.getTestFolder() + "/" + fileName;
            testFileName = test.getTestFolder() + "/" + testFileName;

            filesWithNames.put(testFileName, fileService.getFileWithName("testmain"));
            filesWithNames.put(fileName, FileUtils.getFileBytes(paramFile));
        } else {
            if (testDto.getId() == null) {
                initEnvironment(test, testDto, filesWithNames);
            } else {
                String fileName = testDto.getTestFolder() + "/" + testDto.getParamFileName();
                byte[] file = fileService.getFileWithName(fileName);
                String fileContent = new String(file, CHARSET);
                List<QuestionDto> questions = TestParser.getQuestions(fileContent);
                testDto.setQuestionList(questions);
                byte[] newFile = TestFileGenerator.generateParamFile(testDto, questions);
                filesWithNames.put(testDto.getTestFolder() + "/" + testDto.getParamFileName(), newFile);
            }
        }

        if (newImageUpload && !deleteImage) {
            String imageExtension = FileUtils.getImageExtension(image);
            String newImageName = FileNameGenerator.generateFileName(imageExtension);
            test.setImageFileName(newImageName);
            filesWithNames.put(test.getTestFolder() + "/" + newImageName, FileUtils.getFileBytes(image));
        }
        if (deleteImage) test.setImageFileName(null);

        saveOrUpdate(test, filesWithNames);

        if ((newImageUpload && oldImageExists) || deleteImage)
            fileService.deleteFile(test.getTestFolder() + "/" + oldImageName);
        if (newParamFileUpload && oldParamFileExists && !sameParamFileName) {
            fileService.deleteFile(test.getTestFolder() + "/" + oldParamFileName);
            fileService.deleteFile(test.getTestFolder() + "/" + testDto.getBrcFileName());
        }
    }

    private void initFields(Test test) {
        if (test.getId() == null) test.setStatus(1);
        test.setMinPoints(0.0);
        test.setType(2);
        if (test.getWidth() == null) test.setWidth(0);
        if (test.getHeight() == null) test.setHeight(0);
    }

    private void initEnvironment(Test test, TestDto testDto, Map<String, byte[]> filesWithNames) {
        String dirName = FileNameGenerator.generateFileName("");
        String fileName = FileNameGenerator.generateFileName("");
        String testFileName = fileName + ".php";
        String paramFileName = fileName + "_param.php";

        test.setTestFolder(dirName);
        test.setBrcFileName(testFileName);
        test.setParFileName(paramFileName);

        testFileName = dirName + "/" + testFileName;
        paramFileName = dirName + "/" + paramFileName;

        filesWithNames.put(testFileName, fileService.getFileWithName("testmain"));
        filesWithNames.put(paramFileName, TestFileGenerator.generateParamFile(testDto));
    }

    @Override
    @Transactional
    public void deleteTestWithFiles(Long id) {
        Test test = getTestById(id).orElseThrow(ResourceNotFoundException::new);
        deleteTestById(id);
        String testDirectory = test.getTestFolder();
        File[] testFiles = fileService.getAllFilesFromDirectory(testDirectory);
        if (testFiles == null) return;
        for (File testFile : testFiles)
            fileService.deleteFile(testDirectory + "/" + testFile.getName());
        fileService.deleteFile(testDirectory);
    }

    @Override
    @Transactional
    public void copyTest(TestListDto testTitleDto) {
        Test originTest = getTestById(testTitleDto.getId())
                .orElseThrow(ResourceNotFoundException::new);

        Test test = testMapper.toEntity(testMapper.toDto(originTest));
        test.setId(null);
        test.setTitle(testTitleDto.getTitle());
        test.setType(2);
        test.setStatus(1);
        test.setMinPoints(originTest.getMinPoints());
        String dirName = FileNameGenerator.generateFileName("");
        test.setTestFolder(dirName);

        String originDir = originTest.getTestFolder();
        File[] testFiles = fileService.getAllFilesFromDirectory(originDir);
        Map<String, byte[]> filesWithNames = new HashMap<>();
        if (testFiles == null) throw new TechnicalException();
        for (File testFile : testFiles) {
            String fileName = testFile.getName();
            byte[] file = fileService.getFileWithName(originDir + "/" + fileName);
            filesWithNames.put(dirName + "/" + fileName, file);
        }

        saveOrUpdate(test, filesWithNames);
    }

    @Override
    @Transactional
    public void unarchiveTest(Long id) {
        Test test = getTestById(id).orElseThrow(ResourceNotFoundException::new);
        test.setStatus(1);
        saveOrUpdate(test, new HashMap<>());
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteQuestionByNumber(Long testId, int number) {
        Test test = getTestById(testId).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parFileName = test.getParFileName();
        byte[] parFile = fileService.getFileWithName(folderName + "/" + parFileName);
        String fileContent = new String(parFile, CHARSET);
        List<QuestionDto> questions = TestParser.getQuestions(fileContent);
        TestDto testDto = testMapper.toDto(test, fileContent);

        QuestionDto questionDto = questions.get(number - 1);
        questions.remove(number - 1);
        fileService.deleteFile(test.getTestFolder() + "/" + questionDto.getImageName());
        int[] questionsMax = TestParser.getAllQuestionsNumber(questions);
        int[] questionsCount = testDto.getQuestionsNumber();
        for (int i = 0; i < questionsMax.length; i++) {
            if (questionsCount[i] > questionsMax[i]) questionsCount[i]--;
        }

        byte[] newFile = TestFileGenerator.generateParamFile(testDto, questions);
        boolean fileSaved = fileService.saveFile(newFile, folderName + "/" + parFileName);
        if (!fileSaved)
            throw new TechnicalException("Question with number=" + number + " is not deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAddQuestionPageModelMap(Long testId, ModelMap modelMap) {
        Test test = getTestById(testId).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parName = test.getParFileName();
        String fileName = folderName + "/" + parName;
        byte[] file = fileService.getFileWithName(fileName);
        String fileContent = new String(file, CHARSET);
        int questionNumber = TestParser.getQuestions(fileContent).size();

        QuestionDto questionDto = new QuestionDto();
        questionDto.setAnswers(new String[5]);
        questionDto.setTrueAnswers(new String[5]);
        questionDto.setType(QuestionType.S);
        questionDto.setDifficulty("Лёгкий");
        questionDto.setFolderName(folderName);
        questionDto.setNumber(questionNumber + 1);
        questionDto.setTestId(test.getId());

        modelMap.put("question", questionDto);
        modelMap.put(QUESTION_NUMBER_PARAM, questionNumber + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public void updateQuestion(QuestionDto questionDto) {
        Test test = getTestById(questionDto.getTestId()).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parFileName = test.getParFileName();
        byte[] parFile = fileService.getFileWithName(folderName + "/" + parFileName);
        String fileContent = new String(parFile, CHARSET);
        List<QuestionDto> questions = TestParser.getQuestions(fileContent);
        TestDto testDto = testMapper.toDto(test, fileContent);

        MultipartFile image = questionDto.getImage();
        String oldImageName = questionDto.getImageName();

        boolean newImageUpload = !"".equals(image.getOriginalFilename());
        boolean oldImageExists = (oldImageName != null) && (!"".equals(oldImageName));
        boolean deleteImage = questionDto.isDeleteImage();

        if (newImageUpload && !deleteImage) {
            String imageExtension = FileUtils.getImageExtension(image);
            String newImageName = FileNameGenerator.generateFileName(imageExtension);
            questionDto.setImageName(newImageName);
            boolean fileSaved = fileService.saveFile(FileUtils.getFileBytes(image),
                    test.getTestFolder() + "/" + newImageName);
            if (!fileSaved) throw new TechnicalException();
        }
        if (deleteImage) questionDto.setImageName("");

        if ((newImageUpload && oldImageExists) || deleteImage)
            fileService.deleteFile(test.getTestFolder() + "/" + oldImageName);

        refactorFieldsToServer(questionDto);
        int number = questionDto.getNumber();
        if (number > questions.size()) {
            questions.add(questionDto);
        } else {
            if (questionDto.getOldNumber() != 0)
                questions.remove(questionDto.getOldNumber() - 1);
            questions.add(number - 1, questionDto);
            for (int i = 0; i < questions.size(); i++) {
                QuestionDto question = questions.get(i);
                question.setNumber(i + 1);
                questions.set(i, question);
            }
        }

        byte[] newFile = TestFileGenerator.generateParamFile(testDto, questions);
        boolean fileSaved = fileService.saveFile(newFile, folderName + "/" + parFileName);
        if (!fileSaved)
            throw new TechnicalException();
    }

    private void refactorFieldsToServer(QuestionDto questionDto) {
        if (questionDto.getImageName() == null) questionDto.setImageName("");

        String[] initAnswers = questionDto.getAnswers();
        String[] initTrueAnswers = questionDto.getTrueAnswers();
        List<String> answers = new ArrayList<>();
        List<String> trueAnswers = new ArrayList<>();
        switch (questionDto.getType()) {
            case S:
                int skip = 0;
                for (int i = 0; i < 5; i++) {
                    if (initAnswers[i] == null && i + 1 < Integer.parseInt(initTrueAnswers[0]))
                        skip++;
                    else
                        answers.add(initAnswers[i]);
                }
                trueAnswers.add(String.valueOf(Integer.parseInt(initTrueAnswers[0]) - skip));
                while (answers.size() < 5) answers.add("");
                while (trueAnswers.size() < 5) trueAnswers.add("");
                break;
            case C:
                skip = 0;
                for (int i = 0; i < 5; i++) {
                    if (initAnswers[i] == null)
                        skip++;
                    else
                        answers.add(initAnswers[i]);
                    if (initTrueAnswers[i] != null)
                        trueAnswers.add(String.valueOf(Integer.parseInt(initTrueAnswers[i]) - skip));
                }
                while (answers.size() < 5) answers.add("");
                while (trueAnswers.size() < 5) trueAnswers.add("");
                break;
            case L:
                for (int i = 0; i < 5; i++) {
                    if (initAnswers[i] != null && initTrueAnswers[i] != null) {
                        answers.add(initAnswers[i]);
                        trueAnswers.add(initTrueAnswers[i]);
                    }
                }
                while (answers.size() < 5) answers.add("");
                while (trueAnswers.size() < 5) trueAnswers.add("");
                break;
            case I:
                for (int i = 0; i < 3; i++) {
                    answers.add(initAnswers[i]);
                }
                while (answers.size() < 5) answers.add("");
                break;
            case F:
                for (int i = 0; i < 5; i++) {
                    if (i != 1)
                        answers.add(initAnswers[i] == null ? "" : initAnswers[i]);
                    else
                        answers.add("%");
                }
                break;
            default:
                break;
        }

        questionDto.setAnswers(answers.toArray(new String[0]));
        questionDto.setTrueAnswers(trueAnswers.toArray(new String[0]));
    }

    private void refactorFieldsToClient(QuestionDto questionDto) {
        if (questionDto.getType() == QuestionType.C) {
            String[] initTrueAnswers = questionDto.getTrueAnswers();
            String[] result = new String[] {"", "", "", "", ""};
            for (int i = 0; i < 5; i++) {
                if (!"".equals(initTrueAnswers[i])) {
                    result[Integer.parseInt(initTrueAnswers[i]) - 1] = initTrueAnswers[i];
                }
            }
            questionDto.setTrueAnswers(result);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void fillUpdateQuestionFailedModelMap(Long testId, ModelMap modelMap) {
        Test test = getTestById(testId).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parName = test.getParFileName();
        String fileName = folderName + "/" + parName;
        byte[] file = fileService.getFileWithName(fileName);
        String fileContent = new String(file, CHARSET);
        int questionNumber = TestParser.getQuestions(fileContent).size();

        modelMap.put(QUESTION_NUMBER_PARAM, questionNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditQuestionPageModelMap(Long testId, int number, ModelMap modelMap) {
        Test test = getTestById(testId).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parName = test.getParFileName();
        String fileName = folderName + "/" + parName;
        byte[] file = fileService.getFileWithName(fileName);
        String fileContent = new String(file, CHARSET);
        int questionNumber = TestParser.getQuestions(fileContent).size();

        QuestionDto questionDto = TestParser.getQuestions(fileContent).get(number - 1);
        questionDto.setTestId(test.getId());
        questionDto.setFolderName(test.getTestFolder());
        questionDto.setOldNumber(questionDto.getNumber());
        refactorFieldsToClient(questionDto);

        modelMap.put("question", questionDto);
        modelMap.put(QUESTION_NUMBER_PARAM, questionNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowPreviewPageModelMap(Long id, ModelMap modelMap) {
        TestPreviewDto testDto = getTestById(id)
                .map(testPreviewMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);

        modelMap.put("test", testDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowViewPageModelMap(Long id, ModelMap modelMap) {
        Test test = getTestById(id).orElseThrow(ResourceNotFoundException::new);
        String folderName = test.getTestFolder();
        String parName = test.getParFileName();
        String fileName = folderName + "/" + parName;
        byte[] file = fileService.getFileWithName(fileName);
        String fileContent = new String(file, CHARSET);
        TestViewDto testDto = testViewMapper.toDto(test, fileContent);

        TestAnswerDto answerDto = new TestAnswerDto();
        String[] array = new String[testDto.getQuestionNumber()];
        answerDto.setQuestions(array);
        answerDto.setUserAnswers(array);
        answerDto.setTypes(array);

        modelMap.put("test", testDto);
        modelMap.put("userAnswer", answerDto);
    }
}
