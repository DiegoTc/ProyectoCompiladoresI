class GreatestCommonDivisor:
	a = 10
	b = 20

	def main:
		x = a
		y = b
		z = gcd (x , y)
		
		print z

	def gcd (a, b):
		if b == 0:
			return a
		else:
			return gcd (b, a % b)
