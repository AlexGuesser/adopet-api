package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdocaoRepository extends JpaRepository<Adocao, Long>, JpaSpecificationExecutor<Adocao> {


    boolean existsByPetIdAndStatus(long petId, StatusAdocao status);

    boolean existsByTutorIdAndStatus(long tutorId, StatusAdocao status);

    default long numeroDeAdocoesPorTutor(long tutorId) {
        return this.count((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tutor").get("id"), tutorId));
    }
}
