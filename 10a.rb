# Find first mismatched closing character on lines with mismatched closing characters and sum up their "scores"

input_file = "inputs/10.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n")
end

point_map = {
  ")" => 3,
  "]" => 57,
  "}" => 1197,
  ">" => 25137
}

opening = ["(","[","{","<"]
closing = {
  ")" => "(",
  "]" => "[",
  "}" => "{",
  ">" => "<"
}

points = 0

input.each do |line|
  stack = []
  line.each_char do |ch|
    if opening.include? ch
      stack << ch
    elsif stack[-1] == closing[ch]
      stack.pop
    else
      points += point_map[ch]
      break
    end
  end
end

p points
