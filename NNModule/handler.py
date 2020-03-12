import random as rn

class NN:
    def __init__(self):
        rn.seed(12241)

    def classifySegment(self):
        return rn.randint(0,12)

class Handler:
    
    #"""Version 1"""
    def __init__(self):
        self.phoneticQueue = []
        self.network = NN()

    def classifySegment_V1(self):
        return self.network.classifySegment()#between 1 and 12

    def getPhonetic_V1(self):
         #add buffer to queue
        phonetic = self.classifySegment_V1()
        self.addSegment_V1(phonetic)
        return

    def addSegment_V1(self, Segment):
        self.phoneticQueue.append(Segment)
        return

    def trainAI_V1(self):
        return

    #"""Version 2"""
    #def GetPhonetic_V2(Segment):
     #   ClassifySegment_V2(Segment)


    #def ClassifySegment_V2(Segment):

    #def TrainAI_V2(VoiceSegement):

hand = Handler()

for x in range(0,10):
    hand.getPhonetic_V1()

print(hand.phoneticQueue)

 