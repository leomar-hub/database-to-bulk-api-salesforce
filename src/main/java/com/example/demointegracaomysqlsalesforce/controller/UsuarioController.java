package com.example.demointegracaomysqlsalesforce.controller;
import com.example.demointegracaomysqlsalesforce.model.Usuario;
import com.example.demointegracaomysqlsalesforce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/cadastrar", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar (@RequestBody Usuario usuario){

        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity init(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return new ResponseEntity(Map.of("usuario", usuario.get()),HttpStatus.OK);
    }

    @GetMapping(value = "/listAll", produces = "application/json")
    public ResponseEntity<List<Usuario>> usuario () {
        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
        return new ResponseEntity(Map.of("usuario", list), HttpStatus.OK);
        //return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/text")
    public String delete (@PathVariable("id") Long id){
        usuarioRepository.deleteById(id);
        return "Usuário deletado";
    }

}
