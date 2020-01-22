package eu.bubu1.pushclient.api;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VersionApi {
  /**
   * Return the server api version.
   * 
   * @return Call&lt;Object&gt;
   */
  @GET("version")
  Call<Object> versionGet();
    

}
