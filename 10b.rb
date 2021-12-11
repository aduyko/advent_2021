# Find missing closing character on lines with missing closing characters and sum up their "scores", then return the middle score

input_file = "inputs/10.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n")
end

scores = {
  "(" => 1,
  "[" => 2,
  "{" => 3,
  "<" => 4
}

closing = {
  ")" => "(",
  "]" => "[",
  "}" => "{",
  ">" => "<"
}

all_points = []

input.each do |line|
  points = 0
  stack = []
  line.each_char do |ch|
    if scores.keys.include? ch
      stack << ch
    elsif stack[-1] == closing[ch]
      stack.pop
    else
      # Break if mismatch
      stack = []
      break
    end
  end
  #Count up scores for incomplete lines
  unless stack.length == 0
    until stack.length == 0
      points = points * 5 + scores[stack[-1]]
      stack.pop
    end
    all_points << points
  end
end

#Problem states there is always an odd amount of scores to consider
p all_points.sort[all_points.length/2]
