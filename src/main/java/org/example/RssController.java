package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RssController {

    private final Main mainController;

    @Autowired
    public RssController(Main mainController) {
        this.mainController = mainController;
    }

    @GetMapping(value = "/rss", produces = "application/xml")
    public String rss() {
        StringBuilder rssContent = new StringBuilder();
        rssContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        rssContent.append("<rss version=\"2.0\">");
        rssContent.append("<channel>");
        rssContent.append("<title>Mi Sitio de Futbol</title>");
        rssContent.append("<link>http://localhost:8080</link>");
        rssContent.append("<description>Noticias sobre futbolistas y equipos</description>");

        List<Map<String, Object>> futbolistas = mainController.getFutbolistas().get("futbolistas");
        List<Map<String, Object>> equipos = mainController.getEquipos().get("equipos");

        // Añadir futbolistas al RSS
        for (Map<String, Object> futbolista : futbolistas) {
            rssContent.append("<item>");
            rssContent.append("<title>").append(futbolista.get("nombre")).append("</title>");
            rssContent.append("<link>http://localhost:8080/futbolista/").append(futbolista.get("nombre")).append("</link>");
            rssContent.append("<description>").append(futbolista.get("equipo")).append("</description>");
            rssContent.append("</item>");
        }

        // Añadir equipos al RSS
        for (Map<String, Object> equipo : equipos) {
            rssContent.append("<item>");
            rssContent.append("<title>").append(equipo.get("nombre")).append("</title>");
            rssContent.append("<link>http://localhost:8080/equipo/").append(equipo.get("nombre")).append("</link>");
            rssContent.append("<description>").append(equipo.get("entrenador")).append("</description>");
            rssContent.append("</item>");
        }

        rssContent.append("</channel>");
        rssContent.append("</rss>");

        return rssContent.toString();
    }
}
