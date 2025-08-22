package edu.tienda.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tienda.core.domain.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@GetMapping("/clientes")
	public List<Cliente> getCliente() {
		log.info("Obteniendo lista de clientes");
	
		return clientes;
	}

	@GetMapping("/clientes/{username}")
	public Cliente getCliente(@PathVariable String username) {
		log.info("");
		log.info("Obteniendo cliente con username: {}", username);
		return clientes.stream().filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst().orElseThrow();

	}

	@PostMapping("/clientes")
	public Cliente altaCliente(@RequestBody Cliente cliente) {
		clientes.add(cliente);
		return cliente;
	}

	@PutMapping("/clientes/{username}")
	public Cliente modificarCliente(@RequestBody  Cliente cliente) {
		Cliente clienteEncontrado = clientes.stream()
				.filter(c -> c.getUsername().equalsIgnoreCase(cliente.getUsername())).findFirst()
				.orElseThrow();
		log.info("Modificando cliente con username: {}", cliente.getUsername());
		clienteEncontrado.setPassword(cliente.getPassword());
		clienteEncontrado.setNombre(cliente.getNombre());
		return clienteEncontrado;
	}
	
	@DeleteMapping("/clientes/{username}")
	public void deleteCliente(@PathVariable String username) {
		log.info("Eliminando cliente con username: {}", username);
		Cliente clienteEncontrado = clientes.stream()
				.filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst()
				.orElseThrow();
		log.info("Eliminado el cliente con username: {}", username);
		clientes.remove(clienteEncontrado);
	}

	private List<Cliente> clientes = new ArrayList<>(Arrays.asList(new Cliente("asd", "Juan", "Perez"),
			new Cliente("2L", "Ana", "Gomez"), new Cliente("3M", "Luis", "Lopez")));
}
