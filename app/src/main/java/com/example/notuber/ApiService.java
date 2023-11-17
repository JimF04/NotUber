package com.example.notuber;

import com.example.notuber.Model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfaz que define las operaciones de la API para la comunicación con el servidor.
 */
public interface ApiService {

    /**
     * Método POST para registrar un conductor en el servidor.
     *
     * @param driver Objeto de la clase Driver que contiene la información del conductor.
     * @return Una llamada (Call) sin cuerpo de respuesta.
     */
    @POST("driver/register")
    Call<Void> registerDriver(@Body Driver driver);

    /**
     * Método POST para registrar un empleado en el servidor.
     *
     * @param employee Objeto de la clase Employee que contiene la información del empleado.
     * @return Una llamada (Call) sin cuerpo de respuesta.
     */
    @POST("employee/register")
    Call<Void> registerEmployee(@Body Employee employee);

    /**
     * Método POST para realizar el inicio de sesión de un conductor en el servidor.
     *
     * @param logIn Objeto de la clase LogIn que contiene las credenciales del conductor.
     * @return Una llamada (Call) sin cuerpo de respuesta.
     */
    @POST("driver/login")
    Call<Void> logInDriver(@Body LogIn logIn);

    /**
     * Método POST para realizar el inicio de sesión de un empleado en el servidor.
     *
     * @param logIn Objeto de la clase LogIn que contiene las credenciales del empleado.
     * @return Una llamada (Call) sin cuerpo de respuesta.
     */
    @POST("employee/login")
    Call<Void> logInEmployee(@Body LogIn logIn);

    /**
     * Método POST para enviar una solicitud de amistad al servidor.
     *
     * @param friendRequest Objeto de la clase FriendRequest que contiene la información de la solicitud de amistad.
     * @return Una llamada (Call) sin cuerpo de respuesta.
     */
    @POST("addfriend")
    Call<Void> addFriend(@Body FriendRequest friendRequest);

    /**
     * Método POST para obtener la lista de amigos de un usuario desde el servidor.
     *
     * @param email Correo electrónico del usuario para el que se solicitan los amigos.
     * @return Una llamada (Call) con un cuerpo de respuesta de tipo String que contiene la lista de amigos en formato JSON.
     */
    @POST("friends")
    Call<String> getFriends(@Body String email);

    /**
     * Método GET para obtener la información de los nodos desde el servidor.
     *
     * @return Una llamada (Call) con un cuerpo de respuesta de tipo NodesResponse que contiene la información de los nodos.
     */
    @GET("graph")
    Call<NodesResponse> getNodes();

    /**
     * Método GET para obtener la ubicación de un empleado desde el servidor.
     *
     * @param email Correo electrónico del empleado para el que se solicita la ubicación.
     * @return Una llamada (Call) con un cuerpo de respuesta de tipo ApiResponse que contiene la información de la ubicación.
     */
    @GET("employee/location/{email}")
    Call<ApiResponse> getEmployeeLocation(@Path("email") String email);

    @POST("shortestPathToCompany")
    Call<List<Node>> getShortestPathToCompany(@Body List<String> destinationNames);


}