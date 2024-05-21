package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication
public class Main {

    private Map<String, List<Map<String, Object>>> futbolistas;
    private Map<String, List<Map<String, Object>>> equipos;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public Main() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream isFutbolistas = getClass().getResourceAsStream("/Objects/Futbolistas.json");
             InputStream isEquipos = getClass().getResourceAsStream("/Objects/Equipos.json")) {
            futbolistas = mapper.readValue(isFutbolistas, new TypeReference<Map<String, List<Map<String, Object>>>>() {});
            equipos = mapper.readValue(isEquipos, new TypeReference<Map<String, List<Map<String, Object>>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Map<String, Object>>> getFutbolistas() {
        return futbolistas;
    }

    public Map<String, List<Map<String, Object>>> getEquipos() {
        return equipos;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("futbolistas", futbolistas.get("futbolistas"));
        model.addAttribute("equipos", equipos.get("equipos"));
        return "index";
    }

    @GetMapping("/futbolista/{nombre}")
    public String futbolista(@PathVariable String nombre, Model model) {
        futbolistas.get("futbolistas").stream()
                .filter(f -> f.get("nombre").equals(nombre))
                .findFirst()
                .ifPresent(futbolista -> model.addAttribute("futbolista", futbolista));
        return "futbolista";
    }

    @GetMapping("/equipo/{nombre}")
    public String equipo(@PathVariable String nombre, Model model) {
        equipos.get("equipos").stream()
                .filter(e -> e.get("nombre").equals(nombre))
                .findFirst()
                .ifPresent(equipo -> model.addAttribute("equipo", equipo));
        return "equipo";
    }
}

/*
PROBLEMAS ENCONTRADOS<

-Error al cargar la ruta de json y css (Resolt).
Tuve inconvenientes a la hora de vincular los .json en el main y el .css en los ficheros .html.

Solucion:
Insertar correctamente la ruta relativa



-Incompatibilidad  con versiones en las dependencias (Resolt).
Tras intentar implementar diversas dependencias tuve que ir modificando las versiones hasta encontrar las apropiadas.

Solucion:
Realizar prueba y error con las diferentes versiones utilizando las salidas de consola como referencia.


* */
















