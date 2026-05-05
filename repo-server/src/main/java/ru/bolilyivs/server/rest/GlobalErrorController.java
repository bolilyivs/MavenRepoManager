package ru.bolilyivs.server.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import lombok.extern.slf4j.Slf4j;
import ru.bolilyivs.server.data.exception.RepoException;

@Slf4j
@Controller
public class GlobalErrorController {
    @Error(global = true)
    public HttpResponse<JsonError> error(Throwable e) {
        log.error(e.getMessage(), e);
        return HttpResponse.serverError(new JsonError(e.getLocalizedMessage()));
    }

    @Error(global = true)
    public HttpResponse<JsonError> error(RepoException e) {
        log.error(e.getMessage(), e);
        return HttpResponse.status(e.getHttpStatus()).body(new JsonError(e.getLocalizedMessage()));
    }
}
