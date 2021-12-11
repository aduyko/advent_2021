# Find a depth such that given a list of depths, depth x is in aggregate the least distant

input_file = "inputs/7.txt"
input = File.open(input_file, &:readline).split(",").map(&:to_i).sort

median = 0
if input.length.even?
  median = input[input.length/2 - 1, 2].sum/2
else
  median = input[input.length/2]
end

p input.map { |n| (median - n).abs }.sum
