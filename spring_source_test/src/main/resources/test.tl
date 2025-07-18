ret = 0.01;
int1 = 666;
int2 = 879;
float1 = 12.34;
float2 = 13.44;
name1 = "my peter";
flag1 = int1 >= int1;
flag2 = float1 > int2;
flag3 = flag1 > true;
double1 = (float1 + ret) * int1;
float3 = (float1 + float2) * int1;
int3 = int1 * (int1 + int2);

if int1 < 100 || flag1 do
  if int1 < 100 && flag2 do
    int3 = -1;
  else do
    if int1 > 1000 do
      int1 = -10;
    else if int1 > 100 do
      int1 = -100; //hit
    end
    int3 = -2; //hit
  end
else if int1 > 800 do
  int3 = 10;
else if int1 > 600 do
  int3 = 1000;
else do
  int3 = 1;
end

int2 = 0;
while int2 < 1000 do
  int2 = int2 + 1;
end
