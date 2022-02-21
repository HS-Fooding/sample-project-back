package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void saveImage(Image image) {
        em.persist(image);
    }

    public void saveImages(List<Image> images){
        for(Image image: images)
            em.persist(image);
    }

    public List<Image> findOne(Long id) {
        return em.createQuery("select i from Image i where id = :id", Image.class)
                .setParameter("id", id)
                .getResultList();
    }
}
