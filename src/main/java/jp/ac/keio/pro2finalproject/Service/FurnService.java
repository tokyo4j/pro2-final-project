package jp.ac.keio.pro2finalproject.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.ac.keio.pro2finalproject.Entity.Furn;
import jp.ac.keio.pro2finalproject.Repository.FurnRepository;
import jp.ac.keio.pro2finalproject.Repository.LeaseRepository;
import jp.ac.keio.pro2finalproject.exception.FileAccessException;

@Service
public class FurnService {
    @Autowired
    FurnRepository furnRepository;

    @Autowired
    LeaseRepository leaseRepository;

    // public List<Furn> getFurnsByUserId(long userId) {
    // return leaseRepository.findByUserId(userId);
    // }

    public List<Furn> getAll() {
        return furnRepository.findAll();
    }

    @Transactional
    public void saveFurn(String name, int amount, MultipartFile imgFile) {
        var furn = new Furn();
        furn.setName(name);
        furn.setAmount(amount);
        furn.setImgUrl("test_url");

        if (!imgFile.isEmpty()) {
            var ext = StringUtils.getFilenameExtension(imgFile.getOriginalFilename());
            var filename = UUID.randomUUID().toString() + "." + ext;
            var dest = Paths.get("data", "img", filename);
            try {
                Files.copy(imgFile.getInputStream(), dest);
                furn.setImgUrl("/img/" + filename);
            } catch (IOException e) {
                throw new FileAccessException(e.getMessage());
            }
        }
        furnRepository.save(furn);
    }

    public void deleteFurn(long id) {
        furnRepository.deleteById(id);
    }
}
