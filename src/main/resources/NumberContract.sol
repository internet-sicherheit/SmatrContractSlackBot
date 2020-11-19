pragma solidity ^0.5.7;

contract NumberContract{

    uint256 number;
    uint256 timesFunctionCalled;


      constructor() public {
        timesFunctionCalled = 0;
    }

    event newNumber(uint256 number);



    function storeNumber(uint256 num) public
    {
        emit newNumber(num);
        number = num;
    }

    function giveStoredNumber() public view returns (uint256)
    {
        return number;
    }




}