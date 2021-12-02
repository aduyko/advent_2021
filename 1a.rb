# Count how many times input[n+1] is greater than input[n]

input_file = "inputs/1.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map(&:to_i)
end

count_increasing = 0

last = input[0]
input.each do |n|
  count_increasing += 1 if n > last
  last = n
end

p count_increasing
