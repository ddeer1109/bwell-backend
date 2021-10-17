package com.example.students.eatwell.recipes.ingredients.SpoonacularApi;

public class UrlBuilder {
    private String API_KEY = "1eac70c6679646c495c10b490246131b";
//    private String API_KEY = "11e7a27c2c6c4d3abb6617b3c7521887";
    private int MAX_RESULTS = 10;

    private String baseUrl = "https://api.spoonacular.com/food/ingredients/";

    private String queryUrl = "search?";
    private String ingredientUrl = "%d/information?";

    private String apiParam = "apiKey=%s&";

    private String queryParam = "query=%s&";
    private String numberParam = "number=%d&";
    private String attachMetaInfo = "metaInformation=true";

    private String basicAmount = "amount=%d&unit=%s";

    private int ingrId;
    private String query;
    private int amount;
    private String unit;

    public UrlBuilder(int ingrId) {
        this.ingrId = ingrId;
    }

    public UrlBuilder(int ingrId, int amount, String unit) {
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
            stringBuilder.append(String.format(basicAmount, amount, unit));
        }

        return stringBuilder.toString();



    }
}
