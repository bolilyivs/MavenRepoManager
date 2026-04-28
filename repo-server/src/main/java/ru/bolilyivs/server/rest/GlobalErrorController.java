package ru.bolilyivs.server.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import ru.bolilyivs.server.data.exception.RepoException;

@Controller
public class GlobalErrorController {
    @Error(global = true)
    public HttpResponse<JsonError> error(Throwable e) {
        return HttpResponse.serverError(new JsonError(e.getMessage()));
    }

    @Error(global = true)
    public HttpResponse<JsonError> error(RepoException e) {
        return HttpResponse.status(e.getHttpStatus()).body(new JsonError(e.getMessage()));
    }
}
