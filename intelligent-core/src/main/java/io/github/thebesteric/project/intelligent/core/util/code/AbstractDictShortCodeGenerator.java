package io.github.thebesteric.project.intelligent.core.util.code;

import java.util.Random;

/**
 * DefaultShortCodeGenerator
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-09-30 13:41:44
 */
public abstract class AbstractDictShortCodeGenerator extends AbstractShortCodeGenerator {

    // 字典表
    private static final String[] CODE_DICT = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private final Random random = new Random();

    @Override
    public String doGenerate(int length) {
        return this.getRandomCode(length);
    }

    @Override
    public abstract boolean validate(String shortCode, int times);

    private String getRandomCode(int length) {
        int i = 0;
        StringBuilder randomStr = new StringBuilder();
        int arrayLength = CODE_DICT.length;
        while (i < length) {
            // 随机每个字符
            randomStr.append(CODE_DICT[random.nextInt(arrayLength)]);
            i++;
        }
        return randomStr.toString();
    }
}
