package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {



    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping("/lista-medicos")
    public ResponseEntity<List<DatosListadoMedico>> lista(){
        List<DatosListadoMedico> lista = medicoRepository.listaProcedure().stream().map(DatosListadoMedico:: new).toList();
        return new ResponseEntity(lista, HttpStatus.OK);
    }

    @GetMapping("/menor-medico")
    ResponseEntity<DatosRespuestaMedico> retornarDatosMedMenor(){
        Medico medico = medicoRepository.menorMedProcedure();
        var datosMedico = new DatosRespuestaMedico(medico.getId(),medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return new ResponseEntity(datosMedico, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }


    @GetMapping
    public ResponseEntity<List<DatosListadoMedico>> listadoMedicos(){
        //return medicoRepository.findAll().stream().map(DatosListadoMedico:: new).toList();
        return ResponseEntity.ok(medicoRepository.findByActivoTrue().stream().map(DatosListadoMedico:: new).toList());
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(),medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    /*public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(),medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }
}
