package com.app.axzif.Utils;

import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Dashboard.dto.ProductResponse;
import com.app.axzif.Location.dto.Root;
import com.app.axzif.Login.dto.LoginResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPointInterface {
    @GET("/api/ProductDetails/getAllBrand")
    Call<CategoryRespose> GetBrandByCtgSubctg(@Header("X-API-KEY") String APIkey,
                                              @Query("category_id") String category_id);

    @GET("/api/ProductDetails/getAllColor")
    Call<CategoryRespose> GetColor(@Header("X-API-KEY") String APIkey,
                                    @Query("category_id") String category_id);


    @FormUrlEncoded
    @POST("/api/Login")
    Call<LoginResponse> secureLogin(@Header("X-API-KEY") String APIkey,
                                    @Field("mobile") String name);

    @GET("/api/ProductDetails/CountProductReview")
    Call<LoginResponse> CountProductReview(@Header("X-API-KEY") String APIkey,
                                    @Query("product_id") String product_id);
    @GET("/api/ProductDetails/GetAvgRating_get")
    Call<LoginResponse> ProductReviewRating(@Header("X-API-KEY") String APIkey,
                                           @Query("product_id") String product_id);

    @FormUrlEncoded
    @POST("/api/Login/OtpVerify")
    Call<LoginResponse> verify(@Header("X-API-KEY") String APIkey,
                               @Field("mobile")  String mobile,
                               @Field("otp")  String otp );

    @GET("/api/Category/GetCategory")
    Call<CategoryRespose> Category(@Header("X-API-KEY") String APIkey);

    @GET("/api/Subcategory/GetSubcategory")
    Call<CategoryRespose> GetSubcategory(@Header("X-API-KEY") String APIkey,
                                         @Query("category_id")   String category_id);

    @GET("/api/SubcategoryType/GetSubcategoryType")
    Call<CategoryRespose> GetSubSubcategory(@Header("X-API-KEY") String APIkey,
                                         @Query("category_id")   String category_id,
                                         @Query("subcategory_id")   String subcategory_id);

    @GET("/api/FilterProduct/GetProductList")
    Call<CategoryRespose> GetProductList(@Header("X-API-KEY") String APIkey,
                                         @Query("category_id")   String category_id,
                                         @Query("subcategory_id")   String subcategory_id,
                                         @Query("subcategorytype_id")   String subcategorytype_id);


   @GET("/api/FilterProduct/GetProductListByBrandNdColor")
    Call<CategoryRespose> GetProductListByBrandNdColor(@Header("X-API-KEY") String APIkey,
                                         @Query("category_id")   String category_id,
                                         @Query("subcategory_id")   String subcategory_id,
                                         @Query("brand_id")   String  brand_id,
                                         @Query("min_price")   int min_price,
                                         @Query("max_price")   int max_price,
                                         @Query("color_id") String color_id);

    @GET("/api/Banner")
    Call<CategoryRespose> Banner(@Header("X-API-KEY") String APIkey);

    @GET("/api/FeaturedProduct")
    Call<CategoryRespose> products(@Header("X-API-KEY") String APIkey);
    @GET("/api/Dashboard/offerTypeProduct")
    Call<ProductResponse> getOfferTypeProduct(@Header("X-API-KEY") String APIkey,
                                              @Query("limit") int next,
                                              @Query("prpage") int start,
                                              @Query("offerCategory_type") String categoryType

    );

    @GET("/api/Search/liveSearchByName")
    Call<CategoryRespose> liveSearchByName(@Header("X-API-KEY") String APIkey,
                                           @Query("product") String product_id,
                                           @Query("start") int start,
                                           @Query("next") int next

    );

     @GET("/api/TodayDeals")
     Call<CategoryRespose> TodayDeals(@Header("X-API-KEY") String APIkey);
     @GET("/api/Dashboard")
     Call<CategoryRespose> getAllProduct(@Header("X-API-KEY") String APIkey,
                                         @Query("prpage")  String start,
                                         @Query("limit")  String next

                                         );

    @GET("/api/FeaturedProduct/GetProductImg")
    Call<CategoryRespose> GetProductImg(@Header("X-API-KEY") String APIkey,
                                        @Query("product_id") String product_id);

    @GET("/api/ProductDetails")
    Call<LoginResponse> singleproduct(@Header("X-API-KEY") String APIkey,
                                      @Query("product_id")  String id);

    @GET("/api/ProductDetails/similarProduct")
    Call<ProductResponse> similarproduct(@Header("X-API-KEY") String APIkey,
                                      @Query("product_id")  String id);

    @GET("/maps/api/geocode/json?")
    Call<Root> getLatLong(@Query("address")  String address,
                              @Query("key") String APIkey );






    @FormUrlEncoded
    @POST("/api/Cart/AddToCart")
    Call<LoginResponse> addcart(@Header("X-API-KEY") String APIkey,
                                @Field("product_id") String product_id,
                                @Field("customer_id") String user_id ,
                                @Field("quantity") String quantity );
    @FormUrlEncoded
    @POST("/api/Checkout/SaveOrder")
    Call<LoginResponse> SaveOrder(@Header("X-API-KEY") String APIkey,
                                  @Field("customer_id") String customer_id ,
                                  @Field("address_id") String address_id ,
                                  @Field("payment_mode") String payment_mode ,
                                  @Field("transaction_id") String transaction_id ,
                                  @Field("transaction_status") String transaction_status

    );
    @FormUrlEncoded
    @POST("/api/WishList/AddToWishlist")
    Call<LoginResponse> AddToWishlist(@Header("X-API-KEY") String APIkey,
                                @Field("customer_id") String customer_id,
                                @Field("product_id") String product_id  );


    @PUT("/api/Cart/UpdateCartQuantity")
    Call<LoginResponse> UpdateCartQuantity(@Header("X-API-KEY") String APIkey,
                                @Query("customer_id") String customer_id,
                                @Query("product_id") String product_id ,
                                @Query("quantity") String quantity );

     @DELETE("/api/Cart/DeleteCartProduct")
    Call<LoginResponse> DeleteCartProduct(@Header("X-API-KEY") String APIkey,
                                @Query("customer_id") String customer_id,
                                @Query("product_id") String product_id );

    @FormUrlEncoded
    @POST("/api/Address/SaveAddress")
    Call<LoginResponse> SaveAddress(@Header("X-API-KEY") String APIkey,
                                      @Field("customer_id") String customer_id,
                                      @Field("city") String city,
                                      @Field("state") String state,
                                      @Field("country") String country,
                                      @Field("zip") String zip,
                                      @Field("address") String address,
                                      @Field("landmark") String landmark,
                                      @Field("mobile") String mobile,
                                      @Field("alt_mobile") String alt_mobile,
                                      @Field("name") String name,
                                      @Field("additional_info") String additional_info,
                                      @Field("address_type") String address_type  );

    @FormUrlEncoded
    @POST("/api/Address/UpdateAddress")
    Call<LoginResponse> UpdateAddress(@Header("X-API-KEY") String APIkey,
                                      @Field("customer_id") String customer_id,
                                      @Field("id") String id,
                                      @Field("city") String city,
                                      @Field("state") String state,
                                      @Field("country") String country,
                                      @Field("zip") String zip,
                                      @Field("address") String address,
                                      @Field("landmark") String landmark,
                                      @Field("mobile") String mobile,
                                      @Field("alt_mobile") String alt_mobile,
                                      @Field("additional_info") String additional_info,
                                      @Field("address_type") String address_type  ,
                                      @Field("name") String name  );

    @DELETE("/api/WishList/DeleteWishlistProduct")
    Call<LoginResponse> DeleteWishList(@Header("X-API-KEY") String APIkey,
                                @Query("customer_id") String customer_id,
                                @Query("product_id") String product_id );


    @GET("/api/Cart/GetCartProduct")
    Call<CategoryRespose> cartlist(@Header("X-API-KEY") String APIkey,
                                   @Query("customer_id") String userid);


   @GET("/api/Checkout/GetOrderList")
    Call<CategoryRespose> GetOrderList(@Header("X-API-KEY") String APIkey,
                                    @Query("customer_id") String userid);

     @GET("/api/Address/GetUserAddress")
    Call<CategoryRespose> GetUserAddress(@Header("X-API-KEY") String APIkey,
                                   @Query("customer_id") String userid);


    @GET("/api/WishList/GetWishlistProduct")
    Call<CategoryRespose> GetWishlistProduct(@Header("X-API-KEY") String APIkey,
                                   @Query("customer_id") String userid);

     @DELETE("/api/Address/DeleteUserAddress")
    Call<LoginResponse> DeleteUserAddress(@Header("X-API-KEY") String APIkey,
                                   @Query("customer_id") String customer_id,
                                   @Query("id") String id);

    @Multipart
    @POST("/api/Login/Registration")
    Call<LoginResponse> signup(
                               @Header("X-API-KEY") String APIkey,
                               @Part("userId") RequestBody userId,
                               @Part("name") RequestBody name,
                               @Part("email")  RequestBody email,
                               @Part("password")  RequestBody password,
                               @Part("address1")  RequestBody address1,
                               @Part("address2")  RequestBody address2,
                               @Part("state")  RequestBody state,
                               @Part("country")  RequestBody country,
                               @Part("city")  RequestBody city,
                               @Part("zip")  RequestBody zip,
                               @Part("mobile")  RequestBody mobile,
                               @Part MultipartBody.Part file5
                               );


    @GET("/api/Member/offer")
    Call<CategoryRespose> offer();

    @FormUrlEncoded
    @POST("/api/Member/catsubproduct")
    Call<CategoryRespose> catsubproduct(@Field("type") String type,
                                   @Field("id")  String id);
    @FormUrlEncoded
    @POST("/api/Checkout/UpdateTransactionStatus")
    Call<LoginResponse> UpdateTransactionStatus(
            @Header("X-API-KEY") String APIkey,
            @Field("order_id") String order_id,
            @Field("transaction_id")  String transaction_id,
            @Field("transaction_response")  String transaction_response);
}
