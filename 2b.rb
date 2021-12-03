# Update submarine based on instructions

input_file = "inputs/2.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split }
end

submarine = { :x => 0, :y => 0, :aim => 0 }

input.each do |instruction|
  magnitude = instruction[1].to_i
  case instruction[0]
  when 'forward'
    submarine[:x] += magnitude
    submarine[:y] += magnitude * submarine[:aim]
    if submarine[:y] < 0
      submarine[:y] = 0
    end
  when 'down'
    submarine[:aim] += magnitude
  when 'up'
    submarine[:aim] -= magnitude
  end
end

p submarine[:x]*submarine[:y]
