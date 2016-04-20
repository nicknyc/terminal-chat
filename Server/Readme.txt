server sturcture look like this

        (GUI display)
ServerForm <---------> Server <---- MessageQueueThread
                              <----- ChatServerThread
                              (Message)
