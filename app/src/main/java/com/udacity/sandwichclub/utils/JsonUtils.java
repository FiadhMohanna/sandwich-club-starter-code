package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //new object to return
        Sandwich sandwich = new Sandwich();
        List<String> sandwichIngredientsList;
        List<String> sandwichAKAList;
        

        JSONObject sandwichObject = new JSONObject(json);
        JSONObject sandwichNameObject = sandwichObject.getJSONObject("name");
        
        sandwichIngredientsList = convertJsonToList(sandwichObject.getJSONArray("ingredients"));
        sandwichAKAList = convertJsonToList(sandwichNameObject.getJSONArray("alsoKnownAs"));

        sandwich.setMainName(sandwichNameObject.getString("mainName"));
        sandwich.setPlaceOfOrigin(sandwichObject.getString("placeOfOrigin"));
        sandwich.setDescription(sandwichObject.getString("description"));
        sandwich.setIngredients(sandwichIngredientsList);
        sandwich.setAlsoKnownAs(sandwichAKAList);
        sandwich.setImage(sandwichObject.getString("image"));

        return sandwich;
    }
    private static List<String> convertJsonToList(JSONArray jsonArray) throws JSONException {
        List<String> list= new ArrayList<>();
        if (jsonArray!=null){
            for (int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }
}
