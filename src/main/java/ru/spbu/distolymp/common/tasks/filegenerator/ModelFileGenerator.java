package ru.spbu.distolymp.common.tasks.filegenerator;

import ru.spbu.distolymp.common.tasks.parser.TaskEvaluator;
import ru.spbu.distolymp.dto.admin.models.ModelViewDto;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class ModelFileGenerator {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private ModelFileGenerator() {}

    public static byte[] generateParamFile(String paramsString) {
        StringBuilder fileContent = new StringBuilder();
        TaskEvaluator evaluator = new TaskEvaluator(paramsString);
        Map<String, Object> params = evaluator.getVariableMap();
        fileContent.append("<!DOCTYPE html>\n")
                .append("<html lang=\"ru\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>Param</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<table class=\"border\" style=\"display: none; margin: 0 auto\">\n")
                .append("<tr>\n")
                .append("<th id=\"userGroup\">admin</th>\n")
                .append("<th id=\"userLogin\">admin</th>\n")
                .append("</tr>");
        int i = 1;
        for (Map.Entry<String, Object> param : params.entrySet()) {
            fileContent.append("<tr>\n")
                    .append("<td id=\"random")
                    .append(i).append("name\">")
                    .append(param.getKey())
                    .append("</td>\n")
                    .append("<td id=\"random")
                    .append(i)
                    .append("value\">")
                    .append(param.getValue().toString())
                    .append("</td>\n")
                    .append("</tr>\n");
            i++;
        }
        fileContent.append("<tr>\n")
                .append("<td colspan=\"2\" id=\"randomCount\">")
                .append(params.size())
                .append("</td>")
                .append("</tr>\n")
                .append("</table>\n</body>\n</html>");
        return fileContent.toString().getBytes(CHARSET);
    }

    public static byte[] generateTextFile(String parsedText) {
        String fileContent = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\"\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Текст задания</title>\n" +
                "</head>\n" +
                "<body>\n" +
                parsedText +
                "\n" +
                "</body>\n" +
                "</html>";
        return fileContent.getBytes(CHARSET);
    }

    public static byte[] generateResultFile(ModelViewDto modelDto) {
        StringBuilder fileContent = new StringBuilder();
        List<String> ansNameList = modelDto.getAnswerNameList();
        Map<String, String> varNameComm = modelDto.getVariableNameComment();
        fileContent.append("<!DOCTYPE html>\n")
                .append("<html lang=\"ru\" xmlns:th=\"http://www.thymeleaf.org\"\n")
                .append("xmlns:sec=\"http://www.thymeleaf.org/extras/spring-security\">\n")
                .append("<head>\n")
                .append("<title>Отчёт</title>\n")
                .append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n")
                .append("<script type=\"text/javascript\">\n")
                .append("function checkChar(str, chars) {\n")
                .append("for (var i = 0; i < chars.length; i++) {\n")
                .append("if (str.indexOf(chars.charAt(i)) >= 0) {\n")
                .append("alert(\"Использовать символ \" + chars.charAt(i) + \" в ответах запрещается!\");\n")
                .append("return true;\n")
                .append("}\n")
                .append("}\n")
                .append("return false;\n")
                .append("}\n")
                .append("\n")
                .append("function checkNumber(str) {\n")
                .append("if (!isFinite(str)) {\n")
                .append("alert(\"В ответ можно вводить только число!\");\n")
                .append("return true;\n")
                .append("} else {\n")
                .append("return false;\n")
                .append("}\n")
                .append("}\n")
                .append("\n")
                .append("function checkRegf(event) {\n")
                .append("getUserIP()\n")
                .append("var cancel = false;\n")
                .append("var as = '';\n")
                .append("var input = null;\n")
                .append("var ans = document.getElementsByTagName(\"input\");\n")
                .append("if (ans === undefined) return true;\n")
                .append("if (ans.length === undefined)\n")
                .append("ans = [ans];\n")
                .append("for (var i = 0; i < ans.length; i++) {\n")
                .append("input = ans[i];\n")
                .append("if (input != null) {\n")
                .append("if (input.type === \"text\") {\n")
                .append("as = input.value.replace(/,/g,\".\").replace(/ /g,\"\");\n")
                .append("input.value = as;\n")
                .append("}\n")
                .append("} else as = \"\";\n")
                .append("cancel = checkChar(as, \"*/+%()<>^[]:{}!|~`&$#@;№?\\\"\\\\\");\n")
                .append("if (cancel) break;\n")
                .append("cancel = checkNumber(as);\n")
                .append("if (cancel) break;\n")
                .append("}\n")
                .append("if (!cancel) {\n")
                .append("document.all.idSubmit.disabled = true;\n")
                .append("return true\n")
                .append("} else {\n")
                .append("return false;\n")
                .append("event.preventDefault();\n")
                .append("event.stopImmediatePropagation();\n")
                .append("if (!event.isDefaultPrevented()) {\n")
                .append("event.returnValue = false;\n")
                .append("}\n")
                .append("}\n")
                .append("}\n")
                .append("</script>\n")
                .append("</head>\n")
                .append("\n")
                .append("<body id=\"bodyID\">\n")
                .append("<!--/*@thymesVar id=\"answer\" type=\"ru.spbu.distolymp.dto.entity.answers.AnswerDto\"*/-->\n")
                .append("<!--/*@thymesVar id=\"result\" type=\"ru.spbu.distolymp.dto.admin.models.ModelResultDto\"*/-->\n")
                .append("<input id=\"answerNumber\" type=\"hidden\" value=\"")
                .append(ansNameList.size())
                .append("\"/>\n")
                .append("<div id=\"mainForm\" th:if=\"${result.correctness == null}\">\n")
                .append("<form method=\"post\"\n")
                .append("th:action=\"@{/p-model/submit-answer}\"\n")
                .append("th:object=\"${answer}\"\n")
                .append("onSubmit=\"return checkRegf(event);\"\n")
                .append("accept-charset=\"UTF-8\">\n")
                .append("<input type=\"hidden\" th:attr=\"name='param'\" th:value=\"'")
                .append(modelDto.getVariableNameValue().replaceAll("\\s", ""))
                .append("'\"/>\n")
                .append("<input type=\"hidden\" th:attr=\"name='problemId'\" th:value=\"")
                .append(modelDto.getId())
                .append("\"/>\n")
                .append("<input id=\"userIP\" type=\"hidden\" th:attr=\"name='ip'\"/>\n")
                .append("<input type=\"hidden\" th:attr=\"name='taskStartDateTime'\" th:value=\"'")
                .append(modelDto.getCurrentServerDateTime())
                .append("'\"/>\n")
                .append("<table border align=center>\n")
                .append("<th>Название величины</th>\n<th>Ответ</th>\n");
        for (int i = 0; i < ansNameList.size(); i++) {
            String ansName = ansNameList.get(i);
            fileContent.append("<tr>\n")
                    .append("<td style=\"text-align: center\">\n")
                    .append("<label for=\"")
                    .append(ansName).append("Input\">")
                    .append(varNameComm.getOrDefault("comment_" + ansName, ansName))
                    .append("</label>\n")
                    .append("</td>\n")
                    .append("<td>\n")
                    .append("<input id=\"")
                    .append(ansName)
                    .append("Input\"")
                    .append(" type=\"number\" step=\"any\" maxlength=\"4368\"\n")
                    .append("th:field=\"*{userAnswers[")
                    .append(i)
                    .append("]}\" required/>\n")
                    .append("<span>");
            if (varNameComm.containsKey("commentAfter_" + ansName)) {
                fileContent.append(varNameComm.get("commentAfter_" + ansName));
            }
            fileContent.append("</span>\n")
                    .append("</td>\n")
                    .append("</tr>\n");
        }
        fileContent.append("</table>\n")
                .append("<br>\n")
                .append("<table align=center width=100%>\n")
                .append("<tr>\n")
                .append("<td align=center>\n")
                .append("<input type=\"submit\" id=\"idSubmit\" align=\"center\" value=\"Отправить результаты на сервер\"/>\n")
                .append("</td>\n")
                .append("</tr>\n")
                .append("</table>\n")
                .append("</form>\n")
                .append("</div>\n")
                .append("<div id=\"result\" th:if=\"${result.correctness != null}\">\n")
                .append("<table border align=center>\n")
                .append("<tr style=\"text-align: center\">\n")
                .append("<th id=\"answerName\" style=\"vertical-align: middle; font-weight: bold\">Название</th>\n")
                .append("<th id=\"answer\" style=\"vertical-align: middle; font-weight: bold\">Ответ</th>\n")
                .append("<th id=\"correctness\" style=\"vertical-align: middle; font-weight: bold\">Результат</th>\n")
                .append("<th id=\"points\" style=\"vertical-align: middle; font-weight: bold\">Баллы</th>\n")
                .append("</tr>\n");
        for (int i = 0; i < ansNameList.size(); i++) {
            String ansName = ansNameList.get(i);
            fileContent.append("<tr>\n")
                    .append("<td th:utext=\"'")
                    .append(varNameComm.getOrDefault("comment_" + ansName, ansName))
                    .append("'\"></td>\n")
                    .append("<td th:utext=\"${result.userAnswerMap.get('")
                    .append(ansName)
                    .append("') == null} ? '&nbsp;' : ${result.userAnswerMap.get('")
                    .append(ansName)
                    .append("')}\"></td>\n")
                    .append("<td th:text=\"${result.correctness.get('")
                    .append(ansName)
                    .append("')} ? 'Правильно' : 'Неправильно'\"\n")
                    .append("th:style=\"'color:' + ${__${result.correctness.get('")
                    .append(ansName)
                    .append("')}__ ? '#28A745' : '#DC3545'}\"></td>\n")
                    .append("<td th:text=\"${result.points.get(")
                    .append(i)
                    .append(")}\"></td>\n")
                    .append("</tr>\n");
        }
        fileContent.append("<tr>\n")
                .append("<td colspan=\"3\" style=\"text-align: right; font-weight: bold\">За текущую попытку:</td>\n")
                .append("<td th:text=\"${result.userPoints}\"></td>\n")
                .append("</tr>\n")
                .append("<tr>\n")
                .append("<td colspan=\"4\">&nbsp;</td>\n")
                .append("</tr>\n")
                .append("<tr>\n")
                .append("<td colspan=\"3\" style=\"text-align: right; font-weight: bold\">Штрафных баллов:</td>\n")
                .append("<td th:text=\"'-'\"></td>\n")
                .append("</tr>\n")
                .append("<tr>\n")
                .append("<td colspan=\"3\" style=\"text-align: right; font-weight: bold\">Итого за задание:</td>\n")
                .append("<td th:text=\"${result.userPoints} + ' (из ' + ${result.maxPoint} + ')'\"></td>\n")
                .append("</tr>\n")
                .append("</table>\n")
                .append("</div>\n")
                .append("</body>\n")
                .append("<script type=\"text/javascript\">\n")
                .append("function getUserIP(json) {\n")
                .append("if (document.getElementById('userIP') != null && json != null) {\n")
                .append("document.getElementById('userIP').value = json.ip\n")
                .append("}\n}\n")
                .append("</script>\n")
                .append("<script type=\"text/javascript\" src=\"https://api.ipify.org?format=jsonp&callback=getUserIP\"></script>\n")
                .append("</html>");
        return fileContent.toString().getBytes(CHARSET);
    }
}
