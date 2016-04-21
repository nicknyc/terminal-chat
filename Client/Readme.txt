Client structure looks like this

ClientFrame <--------------------------------- ReceiveMessageThread
    (GUI)     (listening message from server)
- Socket                                        -DataInputStream
- DataInputStream
- DataOutputStream
- groupName
- hostName
