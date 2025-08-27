package edu.tienda.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.tienda.core.domain.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

	@GetMapping
	public ResponseEntity<?> getCliente() {
		log.info("Obteniendo lista de clientes");

		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getCliente(@PathVariable String username) {
		log.info("Obteniendo cliente con username: {}", username);
		return ResponseEntity.ok(
				clientes.stream().filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst().orElseThrow());

	}

	@PostMapping
	public ResponseEntity<?> altaCliente(@RequestBody Cliente cliente) {
		clientes.add(cliente);
		// obtener url de servicio
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
				.buildAndExpand(cliente.getUsername()).toUri();
		return ResponseEntity.created(location).body(cliente);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> modificarCliente(@RequestBody Cliente cliente) {
		Cliente clienteEncontrado = clientes.stream()
				.filter(c -> c.getUsername().equalsIgnoreCase(cliente.getUsername())).findFirst().orElseThrow();
		log.info("Modificando cliente con username: {}", cliente.getUsername());
		clienteEncontrado.setPassword(cliente.getPassword());
		clienteEncontrado.setNombre(cliente.getNombre());
		return ResponseEntity.ok(clienteEncontrado);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteCliente(@PathVariable String username) {
		log.info("Eliminando cliente con username: {}", username);
		Cliente clienteEncontrado = clientes.stream().filter(c -> c.getUsername().equalsIgnoreCase(username))
				.findFirst().orElseThrow();
		log.info("Eliminado el cliente con username: {}", username);
		clientes.remove(clienteEncontrado);
		return ResponseEntity.noContent().build();
	}

	private List<Cliente> clientes = new ArrayList<>(Arrays.asList(new Cliente("asd", "Juan", "Perez"),
			new Cliente("2L", "Ana", "Gomez"), new Cliente("3M", "Luis", "Lopez")));
}
