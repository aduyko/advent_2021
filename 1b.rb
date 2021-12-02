# Assuming a "window" includes 3 consecutive values, count how many consecutive windows increase in value
# Essentially, count how many times input[n+3] is greater than input[n]

input_file = "inputs/1.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map(&:to_i)
end

count_increasing = 0
# Last index for +3 comparison is length-4 so we use ... instead of .. in our range
(0...input.length-3).each do |idx|
  count_increasing += 1 if input[idx+3] > input[idx]
end

p count_increasing
