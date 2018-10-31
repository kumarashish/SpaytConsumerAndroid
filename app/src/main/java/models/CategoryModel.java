package models;

import org.json.JSONObject;

public class CategoryModel {
    int categoryId;
    String categoryName;
    public CategoryModel(JSONObject jsonObject)
    {
        try{
            categoryId=jsonObject.isNull("id")?-1:jsonObject.getInt("id");
            categoryName=jsonObject.isNull("category_name")?"":jsonObject.getString("category_name");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
