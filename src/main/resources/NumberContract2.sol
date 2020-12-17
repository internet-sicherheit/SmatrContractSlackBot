pragma solidity ^0.5.7;

contract NumberContract2{

    uint256 number;
    uint256 timesNumberStored;

    event newNumberStored(uint256 number);
    event calledRequestNumberFunction();


    constructor() public {
        timesNumberStored = 0;
    }


    function storeNumber(uint256 num) public
    {

        number = num;
        emit newNumberStored(num);
        timesNumberStored++;
    }

    function requestNumber() public returns (uint256)
    {
        emit calledRequestNumberFunction();
        return number;
    }

    function getTimesNumberStored() public view  returns (uint256)
    {

        return timesNumberStored;
    }



}