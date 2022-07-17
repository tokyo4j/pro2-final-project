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
import jp.ac.keio.pro2finalproject.exception.DataIntegrityException;
import jp.ac.keio.pro2finalproject.exception.FileAccessException;

@Service
public class FurnService {
    @Autowired
    FurnRepository furnRepository;

    @Autowired
    LeaseRepository leaseRepository;

    public List<Furn> getAll() {
        return furnRepository.findAll();
    }

    @Transactional
    public void saveFurn(String name, Integer amount, MultipartFile imgFile) {
        var furn = new Furn();
        furn.setName(name);
        if (amount <= 0)
            throw new DataIntegrityException("Amount must be positive.");
        furn.setAmount(amount);

        if (imgFile != null && !imgFile.isEmpty()) {
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

    @Transactional
    public void updateFurn(Long furnId, String name, Integer amount, MultipartFile imgFile) {
        var furn = furnRepository.findById(furnId).get();

        if (amount != null) {
            if (amount < furn.getLeasedAmount())
                throw new DataIntegrityException("Leased amount cannot exceed total amount.");
            else
                furn.setAmount(amount);
        }

        if (name != null)
            furn.setName(name);

        if (imgFile != null && !imgFile.isEmpty()) {
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
