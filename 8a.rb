# Figure out how many outputs (after | delimiter) contain 2,3,4, or 7 characters
# (If they represent 1,4,7,8 on a digital clock)

input_file = "inputs/8.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split(" | ").map { |m| m.split(" ")} }
end

p input.map { |n| n[1].select { |m| [2,3,4,7].include? m.length }.length }.sum
