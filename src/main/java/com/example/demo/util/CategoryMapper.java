package com.example.demo.util;
import java.util.Map;

public class CategoryMapper {
    private static final Map<Integer, String> CATEGORY_MAP = Map.of(
        1, "仕事",
        2, "個人",
        3, "その他"
    );

    public static String getCategoryName(int id) {
        return CATEGORY_MAP.getOrDefault(id, "不明");
    }
}