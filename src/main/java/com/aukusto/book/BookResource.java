package com.aukusto.book;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @GET
    public Response all() {
        return Response
                .status(Response.Status.OK)
                .entity(bookService.all())
                .build();
    }

    @POST
    public Response add(BookRequest request) {
        return Response
                .status(Response.Status.OK)
                .entity(bookService.add(request))
                .build();
    }
}

