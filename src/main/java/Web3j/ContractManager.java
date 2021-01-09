package Web3j;

import java.util.ArrayList;

public class ContractManager {



    ArrayList<StoredContract> storedContracts = new ArrayList<>();

    public void storeContract(StoredContract contract) {

        storedContracts.add(contract);

        System.out.println(contract.getContractAddress());

    }


    public String deleteContract(String contractAddress) {

        boolean contractExists = false;
        for (int i = 0; i < storedContracts.size(); i++) {
            if (storedContracts.get(i).getContractAddress().equals(contractAddress)) {
                storedContracts.remove(i);
                contractExists = true;
            }
        }

        if (contractExists)
            return "Contract deleted";
        else
            return "Contract does not exists in the manager";

    }


    public String deleteAllContracts() {
        storedContracts.clear();

        return "All Contracts deleted";

    }

    public StoredContract getContract(String contractAddress) {

        StoredContract a = null;

        for (int i = 0; i < storedContracts.size(); i++) {
            if (storedContracts.get(i).getContractAddress().equals(contractAddress)) {
                a = storedContracts.get(i);
            }
        }

        return a;
    }

    public ArrayList<StoredContract> getStoredContracts() {
        return storedContracts;
    }



}
