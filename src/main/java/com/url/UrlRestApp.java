package com.url;

import com.google.gson.Gson;
import com.url.util.rest.FilterController;
import com.url.util.rest.Path;
import com.url.util.rest.StandardResponse;
import com.url.util.rest.StatusResponse;

import static spark.Spark.*;

public class UrlRestApp {

    public static Gson gson;

    public static void main(String[] args) {

        gson = new Gson();

        port(4568);

        before("*", FilterController.slash);

        path(Path.TICKET_CONTROLLER, () -> {
//            get(Path.ALL, TicketController.getAllTicketes);
//            get(Path.BY_ID, TicketController.getTicketById);
//            get(Path.BY_CIDADE_ID, TicketController.getTicketByCidadeId);
//            delete(Path.BY_ID, TicketController.deleteTicket);
//            put(Path.BY_ID, TicketController.putTicket);
//            post(Path.ADD, TicketController.postTicket);
        });

        path(Path.CIDADE_CONTROLLER, () -> {
//            get(Path.ALL, CidadeController.getAllCidades);
//            get(Path.BY_ID, CidadeController.getCidadeById);
//            delete(Path.BY_ID, CidadeController.deleteCidade);
//            put(Path.BY_ID, CidadeController.putCidade);
//            post(Path.ADD, CidadeController.postCidade);
        });

        get("*", (request, response) -> {
            response.type(Path.CONTENT_TYPE_JSON);
            response.status(404);
            StandardResponse ret = new StandardResponse(StatusResponse.ERROR, "Not Found");

            return gson.toJson(ret);
        });

    }

}
