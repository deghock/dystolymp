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
import ru.spbu.distolymp.common.tasks.filegenerator.TestFileGenerator;
import ru.spbu.distolymp.common.tasks.parser.TestParser;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.tests.api.TestListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.api.TestMapper;
import ru.spbu.distolymp.repository.tasks.TestRepository;
import ru.spbu.distolymp.service.admin.tests.api.TestService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.tasks.TestCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.TestSpecsConverter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TestServiceImpl extends TestCrudServiceImpl implements TestService {
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String TESTS_PARAM = "testList";

    public TestServiceImpl(TestRepository testRepository,
                           TestListMapper testListMapper,
                           ListingProblemCrudService listingProblemCrudService,
                           TestMapper testMapper) {
        super(testRepository, testListMapper, listingProblemCrudService, testMapper);
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
        String fileContent = new String(file, StandardCharsets.UTF_8);
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
        String oldImageName = test.getImageFileName();

        boolean newImageUpload = !"".equals(image.getOriginalFilename());
        boolean oldImageExists = (oldImageName != null) && (!"".equals(oldImageName));
        boolean deleteImage = testDto.isDeleteImage();

        Map<String, byte[]> filesWithNames = new HashMap<>();

        if (testDto.getId() == null) {
            initEnvironment(test, testDto, filesWithNames);
        } else {
            String fileName = testDto.getTestFolder() + "/" + testDto.getParFileName();
            byte[] file = fileService.getFileWithName(fileName);
            String fileContent = new String(file, StandardCharsets.UTF_8);
            List<QuestionDto> questions = TestParser.getQuestions(fileContent);
            testDto.setQuestionList(questions);
            byte[] newFile = TestFileGenerator.generateParamFile(testDto, questions);
            filesWithNames.put(testDto.getTestFolder() + "/" + testDto.getParFileName(), newFile);
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
}
