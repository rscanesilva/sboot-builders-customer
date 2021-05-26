package com.builder.customer;

import com.builder.customer.domain.PersonType;
import com.builder.customer.mock.MocksCommon;
import com.builder.customer.presentation.CustomerRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerAPIFunctionalTest {

    static String idPF;
    static String idPJ;

    @BeforeAll
    public void init(){
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void a_shouldCreateNewCustomer() throws JSONException {
        RequestSpecification request = RestAssured.given();

        JSONObject jsonObj = getJsonObject(MocksCommon.PF, MocksCommon.CPF, "19/10/1991");
        request.header("Content-Type","application/json" );
        request.body(jsonObj.toJSONString());
        Response response = request.post("/api/customer");

        Assert.assertEquals(201, response.getStatusCode());

    }

    @Test
    public void b_shouldReturnCustomerByCPF() throws InterruptedException {
        Thread.sleep(2000);
        JsonPath jsonPath = RestAssured.when()
                .get("/api/customer/pf?cpf={cpf}", MocksCommon.CPF)
                .then()
                .assertThat()
                .extract()
                .jsonPath();

        Assert.assertEquals(MocksCommon.CPF, jsonPath.get("document"));
        this.idPF = jsonPath.get("id");

        RestAssured.when()
                .get("/api/customer/pf?cpf={cpf}", MocksCommon.CPF_WITHOUT_MASK)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void c_shouldUpdateCustomer() throws JSONException {
        RequestSpecification request = RestAssured.given();

        JSONObject jsonObj = getJsonObject(MocksCommon.PF, MocksCommon.CPF, "19/10/2000");
        jsonObj.put("id", this.idPF);
        request.header("Content-Type","application/json" );
        request.body(jsonObj.toJSONString());
        Response response = request.put("/api/customer");

        Assert.assertEquals(201, response.getStatusCode());

    }

    @Test
    public void d_shouldReturnCustomerById(){
        RestAssured.when()
                .get("/api/customer/{id}", this.idPF)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void e_shouldDeleteCustomer() throws InterruptedException {
        RestAssured.when()
                .delete("/api/customer/{id}", this.idPF)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void f_shouldCreateNewCustomerPJ() throws JSONException {
        RequestSpecification request = RestAssured.given();

        JSONObject jsonObj = getJsonObject(MocksCommon.PJ, MocksCommon.CNPJ, "19/10/1991");
        request.header("Content-Type","application/json" );
        request.body(jsonObj.toJSONString());
        Response response = request.post("/api/customer");

        Assert.assertEquals(201, response.getStatusCode());

    }

    @Test
    public void g_shouldReturnCustomerByCNPJ() throws InterruptedException {
        Thread.sleep(2000);
        JsonPath jsonPath = RestAssured.when()
                .get("/api/customer/pj?cnpj={cnpj}", MocksCommon.CNPJ)
                .then()
                .assertThat()
                .extract()
                .jsonPath();

        Assert.assertEquals(MocksCommon.CNPJ, jsonPath.get("document"));
        this.idPJ = jsonPath.get("id");

        RestAssured.when()
                .get("/api/customer/pj?cnpj={cnpj}", MocksCommon.CNPJ_WITHOUT_MASK)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void h_shouldUpdateCustomerPJ() throws JSONException {
        RequestSpecification request = RestAssured.given();

        JSONObject jsonObj = getJsonObject(MocksCommon.PJ, MocksCommon.CNPJ, "19/10/2000");
        jsonObj.put("id", this.idPJ);
        request.header("Content-Type","application/json" );
        request.body(jsonObj.toJSONString());
        Response response = request.put("/api/customer");

        Assert.assertEquals(201, response.getStatusCode());

    }

    @Test
    public void i_shouldReturnCustomerPJById(){
        RestAssured.when()
                .get("/api/customer/{id}", this.idPJ)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void j_shouldDeleteCustomer() throws InterruptedException {
        RestAssured.when()
                .delete("/api/customer/{id}", this.idPJ)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    // ERRORS CASE - NO SEQUENCIAL
    @Test
    public void shouldReturnEmptyCustomerByIdStatus204(){
        RestAssured.when()
                .get("/api/customer/{id}", "00000000-0000-0000-0000-000000000000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnStatus400FromCustomerByCPF(){
        RestAssured.when()
                .get("/api/customer/pf?cpf={cpf}", "000.000.000-00")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnStatus204FromCustomerByCPF(){
        RestAssured.when()
                .get("/api/customer/pf?cpf={cpf}", "603.756.760-31")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnStatus400FromCustomerByCNPJ(){
        RestAssured.when()
                .get("/api/customer/pj?cnpj={cnpj}", "123123")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnStatus204FromCustomerByCNPJ(){
        RestAssured.when()
                .get("/api/customer/pj?cnpj={cnpj}", "46.129.699/0001-95")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private String getCustomerRepresentation() throws JsonProcessingException {
        CustomerRepresentation customerRepresentationMock = MocksCommon.getCustomerRepresentationMock(PersonType.PF);
        ObjectMapper mapper = new ObjectMapper();
        return  mapper.writeValueAsString(customerRepresentationMock);
    }

    private JSONObject getJsonObject(String personType, String document, String birthDate) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", MocksCommon.NAME);
        jsonObj.put("birthDate", birthDate);
        jsonObj.put("personType", personType);
        jsonObj.put("document", document);

        return jsonObj;
    }

}
