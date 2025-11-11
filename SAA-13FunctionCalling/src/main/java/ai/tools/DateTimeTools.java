package ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @className: DateTimeTools
 * @description: TODO 类描述
 * @author: xiapengfei
 * @date: 2025-11-11
 **/
public class DateTimeTools {

    @Tool(description = "獲取當前時間", returnDirect = false)
    public static String getCurrentTime() {
        return LocalDate.now().toString();
    }
}
