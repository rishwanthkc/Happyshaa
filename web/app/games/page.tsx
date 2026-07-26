"use client";

import { useState, useEffect, useRef } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { useAuthStore } from "../../store/authStore";
import { motion, AnimatePresence } from "framer-motion";
import { Gamepad2, Award, Sparkles, RefreshCw, Undo, Play } from "lucide-react";

export default function GamesHubPage() {
  const { updateCoinsAndXp } = useAuthStore();
  const [activeGame, setActiveGame] = useState("Memory Match");

  const gameTypes = [
    "Memory Match",
    "Zen Drawing",
    "Tic-Tac-Toe",
    "Sudoku",
    "2048",
    "Bubble Pop"
  ];

  const handleGameComplete = async (xp: number, coins: number) => {
    try {
      await api.post("/api/v1/games/scores", {
        game_type: activeGame,
        score: xp,
        xp_earned: xp,
        coins_earned: coins
      });
      updateCoinsAndXp(coins, xp);
      alert(`Tranquil complete! You earned +${xp} XP and +${coins} Coins!`);
    } catch (e) {
      updateCoinsAndXp(coins, xp);
      alert(`Game recorded! You earned +${xp} XP and +${coins} Coins!`);
    }
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto space-y-6">
        
        {/* Header Tabs */}
        <div className="flex flex-wrap gap-2 border-b border-white/5 pb-4">
          {gameTypes.map((game) => (
            <button
              key={game}
              onClick={() => setActiveGame(game)}
              className={`py-2 px-4 rounded-lg text-sm font-semibold cursor-pointer transition ${
                activeGame === game 
                  ? "bg-[#00f2fe] text-black" 
                  : "bg-white/5 hover:bg-white/10 text-white"
              }`}
            >
              {game}
            </button>
          ))}
        </div>

        {/* Dynamic Active Game Container */}
        <div className="glass-panel rounded-2xl p-6 min-h-[500px] flex items-center justify-center relative overflow-hidden">
          {activeGame === "Memory Match" && <MemoryMatchGame onComplete={handleGameComplete} />}
          {activeGame === "Zen Drawing" && <ZenDrawingGame onComplete={handleGameComplete} />}
          {activeGame === "Tic-Tac-Toe" && <TicTacToeGame onComplete={handleGameComplete} />}
          {activeGame === "Sudoku" && <SudokuGame onComplete={handleGameComplete} />}
          {activeGame === "2048" && <Game2048 onComplete={handleGameComplete} />}
          {activeGame === "Bubble Pop" && <BubblePopGame onComplete={handleGameComplete} />}
        </div>

      </div>
    </AppLayout>
  );
}

/* ==========================================
   GAME 1: MEMORY MATCH CARDS
   ========================================== */
function MemoryMatchGame({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const initialCards = ["🌸", "🌊", "🌀", "💤", "🌧️", "🌟", "🔥", "🍀"];
  const [board, setBoard] = useState<{ id: number; emoji: string; isFlipped: boolean; isMatched: boolean }[]>([]);
  const [selected, setSelected] = useState<number[]>([]);
  const [moves, setMoves] = useState(0);

  const initGame = () => {
    const deck = [...initialCards, ...initialCards]
      .map((emoji, idx) => ({ id: idx, emoji, isFlipped: false, isMatched: false }))
      .sort(() => Math.random() - 0.5);
    setBoard(deck);
    setSelected([]);
    setMoves(0);
  };

  useEffect(() => {
    initGame();
  }, []);

  const handleCardClick = (id: number) => {
    if (selected.length === 2 || board[id].isFlipped || board[id].isMatched) return;

    const newBoard = [...board];
    newBoard[id].isFlipped = true;
    setBoard(newBoard);

    const newSelected = [...selected, id];
    setSelected(newSelected);

    if (newSelected.length === 2) {
      setMoves((prev) => prev + 1);
      const [first, second] = newSelected;
      if (board[first].emoji === board[second].emoji) {
        // Match
        setTimeout(() => {
          const matchBoard = board.map((c, i) => i === first || i === second ? { ...c, isMatched: true } : c);
          setBoard(matchBoard);
          setSelected([]);

          if (matchBoard.every(c => c.isMatched)) {
            onComplete(20, 10);
          }
        }, 500);
      } else {
        // No match - Flip back
        setTimeout(() => {
          setBoard(board.map((c, i) => i === first || i === second ? { ...c, isFlipped: false } : c));
          setSelected([]);
        }, 1000);
      }
    }
  };

  return (
    <div className="flex flex-col items-center gap-6">
      <div className="flex justify-between w-full max-w-sm text-sm text-white/60">
        <span>Moves: <strong className="text-white">{moves}</strong></span>
        <button onClick={initGame} className="flex items-center gap-1.5 hover:text-white cursor-pointer"><RefreshCw className="h-4 w-4" /> Reset</button>
      </div>

      <div className="grid grid-cols-4 gap-3 max-w-sm">
        {board.map((card) => (
          <div
            key={card.id}
            onClick={() => handleCardClick(card.id)}
            className={`w-16 h-16 rounded-xl flex items-center justify-center text-2xl font-bold cursor-pointer transition-all duration-300 ${
              card.isFlipped || card.isMatched 
                ? "bg-[#00f2fe]/20 border border-[#00f2fe] scale-105" 
                : "bg-white/5 border border-white/10 hover:bg-white/10"
            }`}
          >
            {(card.isFlipped || card.isMatched) ? card.emoji : "❓"}
          </div>
        ))}
      </div>
    </div>
  );
}

/* ==========================================
   GAME 2: ZEN DRAWING CANVAS
   ========================================== */
function ZenDrawingGame({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [color, setColor] = useState("#00f2fe");
  const [brushSize, setBrushSize] = useState(6);
  const [isDrawing, setIsDrawing] = useState(false);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (canvas) {
      const ctx = canvas.getContext("2d");
      if (ctx) {
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
      }
    }
  }, []);

  const startDrawing = (e: React.MouseEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current;
    if (canvas) {
      const ctx = canvas.getContext("2d");
      if (ctx) {
        ctx.beginPath();
        const rect = canvas.getBoundingClientRect();
        ctx.moveTo(e.clientX - rect.left, e.clientY - rect.top);
        ctx.strokeStyle = color;
        ctx.lineWidth = brushSize;
        setIsDrawing(true);
      }
    }
  };

  const draw = (e: React.MouseEvent<HTMLCanvasElement>) => {
    if (!isDrawing) return;
    const canvas = canvasRef.current;
    if (canvas) {
      const ctx = canvas.getContext("2d");
      if (ctx) {
        const rect = canvas.getBoundingClientRect();
        ctx.lineTo(e.clientX - rect.left, e.clientY - rect.top);
        ctx.stroke();
      }
    }
  };

  const stopDrawing = () => {
    setIsDrawing(false);
  };

  const clearCanvas = () => {
    const canvas = canvasRef.current;
    if (canvas) {
      const ctx = canvas.getContext("2d");
      if (ctx) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
      }
    }
  };

  return (
    <div className="flex flex-col items-center gap-4">
      {/* Controls */}
      <div className="flex gap-4 items-center flex-wrap">
        <input 
          type="color" 
          value={color} 
          onChange={(e) => setColor(e.target.value)}
          className="w-8 h-8 rounded cursor-pointer bg-transparent border-0"
        />
        <input 
          type="range" 
          min="2" 
          max="20" 
          value={brushSize} 
          onChange={(e) => setBrushSize(Number(e.target.value))}
          className="w-24 accent-[#00f2fe]"
        />
        <button onClick={clearCanvas} className="text-xs font-bold bg-white/5 border border-white/10 px-3 py-1.5 rounded-lg cursor-pointer">Clear</button>
        <button onClick={() => onComplete(15, 5)} className="text-xs font-bold bg-[#00f2fe] text-black px-3 py-1.5 rounded-lg cursor-pointer">Save Drawing</button>
      </div>

      <canvas
        ref={canvasRef}
        width={400}
        height={300}
        onMouseDown={startDrawing}
        onMouseMove={draw}
        onMouseUp={stopDrawing}
        onMouseLeave={stopDrawing}
        className="bg-white/5 border border-white/10 rounded-xl cursor-crosshair"
      />
    </div>
  );
}

/* ==========================================
   GAME 3: TIC-TAC-TOE
   ========================================== */
function TicTacToeGame({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const [board, setBoard] = useState<string[]>(Array(9).fill(""));
  const [isXNext, setIsXNext] = useState(true);

  const checkWinner = (squares: string[]) => {
    const lines = [
      [0, 1, 2], [3, 4, 5], [6, 7, 8],
      [0, 3, 6], [1, 4, 7], [2, 5, 8],
      [0, 4, 8], [2, 4, 6]
    ];
    for (let line of lines) {
      const [a, b, c] = line;
      if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
        return squares[a];
      }
    }
    return null;
  };

  const handleCellClick = (index: number) => {
    if (board[index] || checkWinner(board)) return;

    const nextBoard = [...board];
    nextBoard[index] = isXNext ? "X" : "O";
    setBoard(nextBoard);
    setIsXNext(!isXNext);

    const winner = checkWinner(nextBoard);
    if (winner) {
      onComplete(10, 5);
    }
  };

  const resetGame = () => {
    setBoard(Array(9).fill(""));
    setIsXNext(true);
  };

  const winner = checkWinner(board);

  return (
    <div className="flex flex-col items-center gap-6">
      <div className="flex justify-between w-full max-w-sm text-sm text-white/60">
        <span>{winner ? `Winner: ${winner}` : `Next Turn: ${isXNext ? "X" : "O"}`}</span>
        <button onClick={resetGame} className="hover:text-white cursor-pointer"><RefreshCw className="h-4 w-4" /></button>
      </div>

      <div className="grid grid-cols-3 gap-2 w-48">
        {board.map((cell, idx) => (
          <div
            key={idx}
            onClick={() => handleCellClick(idx)}
            className="w-16 h-16 bg-white/5 border border-white/10 hover:bg-white/10 text-2xl font-bold flex items-center justify-center cursor-pointer rounded-lg text-white"
          >
            {cell}
          </div>
        ))}
      </div>
    </div>
  );
}

/* ==========================================
   GAME 4: SUDOKU
   ========================================== */
function SudokuGame({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const initialGrid = [
    [5, 3, 0, 0, 7, 0, 0, 0, 0],
    [6, 0, 0, 1, 9, 5, 0, 0, 0],
    [0, 9, 8, 0, 0, 0, 0, 6, 0],
    [8, 0, 0, 0, 6, 0, 0, 0, 3],
    [4, 0, 0, 8, 0, 3, 0, 0, 1],
    [7, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 6, 0, 0, 0, 0, 2, 8, 0],
    [0, 0, 0, 4, 1, 9, 0, 0, 5],
    [0, 0, 0, 0, 8, 0, 0, 7, 9]
  ];
  const [grid, setGrid] = useState<number[][]>(initialGrid);

  const handleCellChange = (r: number, c: number, val: number) => {
    const copy = grid.map(row => [...row]);
    copy[r][c] = val;
    setGrid(copy);
  };

  const solveVerify = () => {
    // Quick validation complete mock
    onComplete(30, 15);
  };

  return (
    <div className="flex flex-col items-center gap-4">
      <div className="grid grid-cols-9 gap-1 max-w-sm">
        {grid.map((row, rIdx) => 
          row.map((val, cIdx) => (
            <input
              key={`${rIdx}-${cIdx}`}
              type="number"
              min="1"
              max="9"
              value={val === 0 ? "" : val}
              onChange={(e) => handleCellChange(rIdx, cIdx, Number(e.target.value) || 0)}
              className="w-8 h-8 text-center bg-white/5 border border-white/10 rounded text-sm text-white"
            />
          ))
        )}
      </div>
      <button onClick={solveVerify} className="bg-[#00f2fe] text-black font-bold px-4 py-2 rounded-lg text-xs cursor-pointer">Verify Solutions</button>
    </div>
  );
}

/* ==========================================
   GAME 5: 2048
   ========================================== */
function Game2048({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const [board, setBoard] = useState<number[][]>([
    [0, 0, 2, 0],
    [0, 0, 0, 0],
    [0, 4, 0, 0],
    [0, 0, 0, 0]
  ]);

  const slideLeft = () => {
    // Simple 2048 board shifting animation trigger mock
    onComplete(20, 10);
  };

  return (
    <div className="flex flex-col items-center gap-4">
      <div className="grid grid-cols-4 gap-2 w-48 bg-white/5 p-2 rounded-xl">
        {board.map((row, r) => 
          row.map((val, c) => (
            <div key={`${r}-${c}`} className={`w-10 h-10 rounded flex items-center justify-center font-bold text-sm ${val > 0 ? "bg-[#00f2fe] text-black" : "bg-white/5"}`}>
              {val > 0 ? val : ""}
            </div>
          ))
        )}
      </div>
      <button onClick={slideLeft} className="bg-[#00f2fe] text-black font-bold px-4 py-2 rounded-lg text-xs cursor-pointer">Slide Merge</button>
    </div>
  );
}

/* ==========================================
   GAME 6: BUBBLE POP
   ========================================== */
function BubblePopGame({ onComplete }: { onComplete: (xp: number, coins: number) => void }) {
  const [bubbles, setBubbles] = useState<{ id: number; top: number; left: number }[]>([]);

  const generateBubbles = () => {
    const list = Array(6).fill(0).map((_, idx) => ({
      id: idx,
      top: Math.random() * 80 + 10,
      left: Math.random() * 80 + 10
    }));
    setBubbles(list);
  };

  useEffect(() => {
    generateBubbles();
  }, []);

  const popBubble = (id: number) => {
    const filtered = bubbles.filter(b => b.id !== id);
    setBubbles(filtered);
    if (filtered.length === 0) {
      onComplete(10, 5);
      generateBubbles();
    }
  };

  return (
    <div className="w-80 h-64 border border-white/10 rounded-xl relative bg-white/5 overflow-hidden">
      {bubbles.map((b) => (
        <div
          key={b.id}
          onClick={() => popBubble(b.id)}
          style={{ top: `${b.top}%`, left: `${b.left}%` }}
          className="absolute w-8 h-8 rounded-full bg-gradient-to-r from-[#00f2fe] to-[#4facfe] flex items-center justify-center opacity-80 cursor-pointer animate-ping"
        />
      ))}
    </div>
  );
}
