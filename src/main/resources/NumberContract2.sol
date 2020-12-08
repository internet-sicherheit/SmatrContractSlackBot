pragma solidity ^0.5.7;

contract NumberContract2{

    uint256 number;
    uint256 timesNumberRequested;

     event newNumber(uint256 number);
    event calledRequestNumber();
    event calledGetTimesFunctionCalled();



      constructor() public {
        timesNumberRequested = 0;
    }



    function storeNumber(uint256 num) public
    {
        emit newNumber(num);
        number = num;
    }

    function requestNumber() public  returns (uint256)
    {
        emit calledRequestNumber();
        return number;
    }

    function getTimesFunctionCalled() public  returns (uint256)
    {
        emit  calledGetTimesFunctionCalled();
        return timesNumberRequested;
    }



}