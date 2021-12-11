# There's a complicated clock thing that's a lot of trouble to explain but assume that each letter corresponds to a section of a number on a digital clock. Figure out what the numbers after the | delimiter represent, then add them up

input_file = "inputs/8test.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split(" | ").map { |m| m.split(" ").map { |p| p.chars.sort.join } } }
end

solution = {
  :a => [],
  :b => [],
  :c => [],
  :d => [],
  :e => [],
  :f => [],
  :g => []
}

input.each do |line|
  result = line[1]
  numbers = line.flatten.uniq
  p result
  p numbers
  p " "
end

p input
p input.map { |n| n[1].select { |m| [2,3,4,7].include? m.length }.length }.sum
