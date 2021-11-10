package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.configuration.ApiKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {
    private String API_KEY;
    private int MAX_RESULTS = 10;

    private String baseUrl = "https://api.spoonacular.com/food/ingredients/";

    private String queryUrl = "search?";
    private String ingredientUrl = "%d/information?";

    private String apiParam = "apiKey=%s&";

    private String queryParam = "query=%s&";
    private String numberParam = "number=%d&";
    private String attachMetaInfo = "metaInformation=true";

    private String basicAmount = "amount=%s&unit=%s";

    private int ingrId;
    private String query;
    private double amount;
    private String unit;
    private final ApiKeyConfig config;

    @Autowired
    public UrlBuilder(@Lazy ApiKeyConfig config){
        this.config = config;
    }

    public UrlBuilder query(String query){
        this.query = query;
        return this;
    }
    public UrlBuilder ingredient(int ingrdId){
        this.ingrId = ingrdId;
        return this;
    }
    public UrlBuilder amount(int amount){
        this.amount = amount;
        return this;
    }
    public UrlBuilder amount(double amount){
        this.amount = amount;
        return this;
    }
    public UrlBuilder unit(String unit){
        this.unit = unit;
        return this;
    }


    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(baseUrl);

        if (query != null) {
            stringBuilder.append(queryUrl);
            stringBuilder.append(String.format(apiParam, config.getApiKey()));
            stringBuilder.append(String.format(queryParam, query));
            stringBuilder.append(String.format(numberParam, MAX_RESULTS));
            stringBuilder.append(attachMetaInfo);
        } else {
            stringBuilder.append(String.format(ingredientUrl, ingrId));
            stringBuilder.append(String.format(apiParam, config.getApiKey()));
            stringBuilder.append(String.format(basicAmount, String.valueOf(amount), unit));
        }
        query = null;
        ingrId = 0;
        amount = 0;
        unit = "";

        System.out.println(config.getApiKey());
        System.out.println(config.getApiKey());
        return stringBuilder.toString();



    }
}
