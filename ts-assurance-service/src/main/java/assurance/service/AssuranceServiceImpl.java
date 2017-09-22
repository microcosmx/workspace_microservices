package assurance.service;

import assurance.domain.*;
import assurance.repository.AssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AssuranceServiceImpl implements AssuranceService {

    @Autowired
    private AssuranceRepository assuranceRepository;

//    @Override
//    public Assurance createAssurance(Assurance assurance) {
//        Assurance assuranceTemp = assuranceRepository.findById(assurance.getId());
//        if(assuranceTemp != null){
//            System.out.println("[Assurance Service][Init Assurance] Already Exists Id:" + assurance.getId());
//        } else {
//            assuranceRepository.save(assurance);
//        }
//        return assurance;
//    }

    @Override
    public Assurance findAssuranceById(UUID id) {
        return assuranceRepository.findById(id);
    }

    @Override
    public Assurance findAssuranceByOrderId(UUID orderId) {
        return assuranceRepository.findByOrderId(orderId);
    }

    @Override
    public AddAssuranceResult create(AddAssuranceInfo aai) {
        Assurance a = assuranceRepository.findByOrderId(UUID.fromString(aai.getOrderId()));
        AddAssuranceResult aar = new AddAssuranceResult();
        AssuranceType at = AssuranceType.getTypeByIndex(aai.getTypeIndex());
        if(a != null){
            System.out.println("[Assurance-Add&Delete-Service][AddAssurance] Fail.Assurance already exists");
            aar.setStatus(false);
            aar.setMessage("Assurance Already Exists");
            aar.setAssurance(null);
        } else if(at == null){
            System.out.println("[Assurance-Add&Delete-Service][AddAssurance] Fail.Assurance type doesn't exist");
            aar.setStatus(false);
            aar.setMessage("Assurance type doesn't exist");
            aar.setAssurance(null);
        } else{
            Assurance assurance = new Assurance(UUID.randomUUID(), UUID.fromString(aai.getOrderId()), at);
            assuranceRepository.save(assurance);
            System.out.println("[Assurance-Add&Delete-Service][AddAssurance] Success.");
            aar.setStatus(true);
            aar.setMessage("Success");
            aar.setAssurance(assurance);
        }
        return aar;
    }

    @Override
    public DeleteAssuranceResult deleteById(UUID assuranceId) {
        assuranceRepository.deleteById(assuranceId);
        Assurance a = assuranceRepository.findById(assuranceId);
        DeleteAssuranceResult dar = new DeleteAssuranceResult();
        if(a == null){
            System.out.println("[Assurance-Add&Delete-Service][DeleteAssurance] Success.");
            dar.setStatus(true);
            dar.setMessage("Success");
        } else {
            System.out.println("[Assurance-Add&Delete-Service][DeleteAssurance] Fail.Assurance not clear.");
            dar.setStatus(false);
            dar.setMessage("Reason Not clear");
        }
        return dar;
    }

    @Override
    public DeleteAssuranceResult deleteByOrderId(UUID orderId) {
        assuranceRepository.removeAssuranceByOrderId(orderId);
        Assurance a = assuranceRepository.findByOrderId(orderId);
        DeleteAssuranceResult dar = new DeleteAssuranceResult();
        if(a == null){
            System.out.println("[Assurance-Add&Delete-Service][DeleteAssurance] Success.");
            dar.setStatus(true);
            dar.setMessage("Success");
        } else {
            System.out.println("[Assurance-Add&Delete-Service][DeleteAssurance] Fail.Assurance not clear.");
            dar.setStatus(false);
            dar.setMessage("Reason Not clear");
        }
        return dar;
    }

    @Override
    public ModifyAssuranceResult modify(ModifyAssuranceInfo info) {
        Assurance oldAssurance = findAssuranceById(UUID.fromString(info.getAssuranceId()));
        ModifyAssuranceResult mcr = new ModifyAssuranceResult();
        if(oldAssurance == null){
            System.out.println("[Assurance-Modify-Service][ModifyAssurance] Fail.Assurance not found.");
            mcr.setStatus(false);
            mcr.setMessage("Contacts not found");
            mcr.setAssurance(null);
        }else{
            oldAssurance.setType(AssuranceType.getTypeByIndex(info.getTypeIndex()));
            assuranceRepository.save(oldAssurance);
            System.out.println("[Assurance-Modify-Service][ModifyAssurance] Success.");
            mcr.setStatus(true);
            mcr.setMessage("Success");
            mcr.setAssurance(oldAssurance);
        }
        return mcr;
    }

    @Override
    public GetAllAssuranceResult getAllAssurances() {
        ArrayList<Assurance> as = assuranceRepository.findAll();
        GetAllAssuranceResult gar = new GetAllAssuranceResult();
        gar.setStatus(true);
        gar.setMessage("Success");
        gar.setAssurances(as);
        return gar;
    }

    @Override
    public GetAllAssuranceTypeResult getAllAssuranceTypes() {
        List<AssuranceType> atlist = Arrays.asList(AssuranceType.values());
        GetAllAssuranceTypeResult gaatr = new GetAllAssuranceTypeResult();
        gaatr.setStatus(true);
        gaatr.setMessage("Success");
        gaatr.setAssuranceTypes(atlist);
        return gaatr;
    }
}
