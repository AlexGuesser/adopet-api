package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TutorRepository extends JpaRepository<Tutor, Long>, JpaSpecificationExecutor<Tutor> {

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

    default boolean existeOutroTutorComEsseEmail(long tutorId, String email) {
        return this.exists(
                (root, query, criteriaBuilder) -> {
                    return criteriaBuilder.and(
                            criteriaBuilder.not(criteriaBuilder.equal(root.get("id"), tutorId)),
                            criteriaBuilder.equal(root.get("email"), email)
                    );
                }
        );
    }

    default boolean existeOutroTutorComEsseTelefone(long tutorId, String telefone) {
        return this.exists(
                (root, query, criteriaBuilder) -> {
                    return criteriaBuilder.and(
                            criteriaBuilder.not(criteriaBuilder.equal(root.get("id"), tutorId)),
                            criteriaBuilder.equal(root.get("telefone"), telefone)
                    );
                }
        );
    }

}
