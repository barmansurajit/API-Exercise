package com.contsol.pax.swagger;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(tags = {
        @Tag(name = "passengers", description = "Operations related to passengers")
},
        info = @Info(title = "API - Passenger Service",
                version = "0.0.1",
                contact = @Contact(name = "Surajit Barman",
                        url = "",
                        email = "support@tech.com"),
                license = @License(name = "Apache 2.0",
                url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
public class ResourceApplication extends Application {
}