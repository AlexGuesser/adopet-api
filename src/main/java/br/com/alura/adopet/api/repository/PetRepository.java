package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {

    default List<Pet> pegaTodosDisponiveis() {
        return this.findAll(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("adotado"), false)
        );
    }

}
