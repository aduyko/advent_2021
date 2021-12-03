# Update coordinates based on instructions

input_file = "inputs/2.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split }
end

coordinates = { :x => 0, :y => 0 }

input.each do |instruction|
  case instruction[0]
  when 'forward'
    coordinates[:x] += instruction[1].to_i
  when 'down'
    coordinates[:y] += instruction[1].to_i
  when 'up'
    coordinates[:y] -= instruction[1].to_i
    if coordinates[:y] < 0
      coordinates[:y] = 0
    end
  end
end

p coordinates[:x]*coordinates[:y]
