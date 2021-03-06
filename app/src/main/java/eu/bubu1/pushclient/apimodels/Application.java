/*
 * Pushserver API
 * HTTP API for the open pushserver implementation.
 *
 * The version of the OpenAPI document: 0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package eu.bubu1.pushclient.apimodels;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * A registered application installed on a client
 */
@ApiModel(description = "A registered application installed on a client")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-01-23T17:14:54.483+01:00[Europe/Berlin]")
public class Application {
  public static final String SERIALIZED_NAME_REGISTRATION_ID = "registration_id";
  @SerializedName(SERIALIZED_NAME_REGISTRATION_ID)
  private String registrationId;

  public static final String SERIALIZED_NAME_ROUTING_TOKEN = "routing_token";
  @SerializedName(SERIALIZED_NAME_ROUTING_TOKEN)
  private String routingToken;


  public Application registrationId(String registrationId) {
    
    this.registrationId = registrationId;
    return this;
  }

   /**
   * Get registrationId
   * @return registrationId
  **/
  @ApiModelProperty(example = "XXAAj76gkjlsfdhalSKL", required = true, value = "")

  public String getRegistrationId() {
    return registrationId;
  }


  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }


   /**
   * Token to target a mobile application.
   * @return routingToken
  **/
  @ApiModelProperty(example = "8904j76gkjlsfdhalSKL", required = true, value = "Token to target a mobile application.")

  public String getRoutingToken() {
    return routingToken;
  }




  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Application application = (Application) o;
    return Objects.equals(this.registrationId, application.registrationId) &&
        Objects.equals(this.routingToken, application.routingToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registrationId, routingToken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Application {\n");
    sb.append("    registrationId: ").append(toIndentedString(registrationId)).append("\n");
    sb.append("    routingToken: ").append(toIndentedString(routingToken)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

