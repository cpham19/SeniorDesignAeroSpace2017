
# coding: utf-8

# In[10]:


import unittest

class MathTestCase(unittest.TestCase):
    
    # Initialize variables
    def setUp(self):
        self.a = 5
        self.b = 5

    # Clean up variables after each test    
    def tearDown(self):
        self.a = 0
        self.b = 0
        
    # Test for addition    
    def test_Add(self):
        sum = BasicMath.add(self.a, self.b)
        self.assertEqual(sum, 10)
      
    # Test for subtraction
    def test_Sub(self):
        sub = BasicMath.sub(self.a, self.b)
        self.assertEqual(sub, 0)
        
    # Test for multiplication    
    def test_Multi(self):
        multi = BasicMath.multi(self.a, self.b)
        self.assertEqual(multi, 25)

    # Test for division
    def test_Div(self):
        div = BasicMath.div(self.a, self.b)
        self.assertEqual(div, 1)


class BasicMath() :
    
    def add(a, b):
        return a + b
    
    def sub(a, b):
        return a - b
    
    def multi(a, b):
        return a * b
    
    def div(a, b) :
        return a / b
    
    
# Run tests
unittest.main(argv=['first-arg-is-ignored'], exit=False)
        

