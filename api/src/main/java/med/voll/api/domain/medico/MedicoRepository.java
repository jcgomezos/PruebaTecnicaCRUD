package med.voll.api.domain.medico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Collection<Medico> findByActivoTrue();

    @Query(value = "{call lista_procedure()}", nativeQuery = true)
    public List<Medico> listaProcedure();

    @Query(value = "{call medico_menor()}", nativeQuery = true)
    public Medico menorMedProcedure();
}
