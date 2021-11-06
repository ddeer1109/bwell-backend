package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

public class UrlBuilder {
//    private String API_KEY = "1eac70c6679646c495c10b490246131b";
    private String API_KEY = "c78a5fafd707476389cd614fcbfc24bf";
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

    public UrlBuilder(int ingrId) {
        this.ingrId = ingrId;
    }

    public UrlBuilder(int ingrId, double amount, String unit) {
        this.ingrId = ingrId;
        this.amount = amount;
        this.unit = unit;
    }

    public UrlBuilder(String query) {
        this.query = query;
    }


    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(baseUrl);

        if (query != null) {
            stringBuilder.append(queryUrl);
            stringBuilder.append(String.format(apiParam, API_KEY));
            stringBuilder.append(String.format(queryParam, query));
            stringBuilder.append(String.format(numberParam, MAX_RESULTS));
            stringBuilder.append(attachMetaInfo);
        } else {
            stringBuilder.append(String.format(ingredientUrl, ingrId));
            stringBuilder.append(String.format(apiParam, API_KEY));
            stringBuilder.append(String.format(basicAmount, String.valueOf(amount), unit));
        }

        return stringBuilder.toString();



    }
}
