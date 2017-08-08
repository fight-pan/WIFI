package com.quark.wificontrol.util;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by XIAO-Y on 2016/8/25.
 * >#
 */
public class PinYinUtil {

    /**
     * 获取汉字的拼音, 会消耗一定的资源，该方法不应该被频繁调用
     * @param chinese
     * @return
     */
    public static String getPinYin(String chinese){
        if(TextUtils.isEmpty(chinese))return null;

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();//控制转化的拼音的格式
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置转化的拼音是大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置转化的拼音不需要声调

        //由于只能获取单个汉字的拼音，所以需要将字符串转成字符数组，去遍历转化
        char[] charArray = chinese.toCharArray();
        StringBuilder sb = new StringBuilder();
        //对每个汉字进行转化，然后将每个汉字的拼音拼接起来
        for (int i = 0; i < charArray.length; i++) {
            //如果当前的字符是空格，那么则忽略处理
            if(Character.isWhitespace(charArray[i]))continue;

            //**奥巴#马,。，--
            //汉字占2个字节,所以汉字肯定大于127，
            if(charArray[i]>127){
                //说明有可能是汉字
                try {
                    //由于多音字的存在， 比如单: dan  san
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);
                    if(pinyinArr!=null){
                        //注意：此处即使是多音字，由于没法判断应该取哪个，所以临时取了第一个
                        sb.append(pinyinArr[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    //说明当前不是正确的汉字,比如：O(∩_∩)O~,则可以忽略，什么都不处理
                }
            }else {
                //绝对不是汉字，一般是键盘上能够输入的字符,可以选择直接拼接
                sb.append(charArray[i]);
            }
        }

        return sb.toString();
    }
}
