<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import ArticleCard from '../components/ArticleCard.vue';
import IconSearch from '../components/icons/IconSearch.vue';

const { t } = useI18n();
const route = useRoute();
const router = useRouter();

const searchQuery = ref('');
const activeFilter = ref('all');
const filters = ['all', 'article', 'tag', 'author'];

const isLoading = ref(false);

// Pagination
const currentPage = ref(1);
const itemsPerPage = 10;

const searchHistory = ref<string[]>([]);
const hotSearches = ref(['Spring Boot 3', 'Kubernetes', 'Microservices', 'AI Tools', 'Vue 3', 'React', 'Docker']);

// Mock results
const generateMockResults = () => {
  const results = [];
  const topics = ['Vue 3', 'React', 'Angular', 'Spring Boot', 'Java', 'Python', 'Docker', 'Kubernetes', 'AWS', 'Azure'];
  
  for (let i = 1; i <= 35; i++) {
    const topic = topics[i % topics.length];
    results.push({
      id: i,
      title: `${topic} Guide Part ${Math.floor(i / 10) + 1} - Advanced Techniques`,
      summary: `This is a detailed summary for article ${i} about ${topic}. Learn how to master ${topic} with practical examples and best practices.`,
      date: `2025-${10 + (i % 2)}-${(i % 28) + 1}`,
      tags: [topic, 'Tech', 'Programming'],
      image: '',
      views: Math.floor(Math.random() * 5000) + 100
    });
  }
  return results;
};

const mockResults = generateMockResults();

// Computed properties
const filteredResults = computed(() => {
  if (!searchQuery.value) return [];
  
  const query = searchQuery.value.toLowerCase();
  let results = mockResults.filter(item => {
    // Text Search
    const matchTitle = item.title.toLowerCase().includes(query);
    const matchSummary = item.summary.toLowerCase().includes(query);
    const matchTags = item.tags.some(tag => tag.toLowerCase().includes(query));
    
    if (activeFilter.value === 'article') return matchTitle || matchSummary;
    if (activeFilter.value === 'tag') return matchTags;
    // if (activeFilter.value === 'author') return matchAuthor; // Add author if needed
    
    return matchTitle || matchSummary || matchTags;
  });

  return results;
});

const totalPages = computed(() => Math.ceil(filteredResults.value.length / itemsPerPage));

const paginatedResults = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredResults.value.slice(start, end);
});

// Methods
const performSearch = async () => {
  if (!searchQuery.value) return;

  isLoading.value = true;
  
  // Update URL without reloading
  if (route.query.q !== searchQuery.value) {
    router.push({ query: { ...route.query, q: searchQuery.value } });
  }

  // Save to history
  addToHistory(searchQuery.value);
  
  // Reset pagination
  currentPage.value = 1;

  // Simulate API delay
  setTimeout(() => {
    isLoading.value = false;
  }, 800);
};

const addToHistory = (query: string) => {
  const cleanQuery = query.trim();
  if (!cleanQuery) return;

  const index = searchHistory.value.indexOf(cleanQuery);
  if (index > -1) {
    searchHistory.value.splice(index, 1);
  }
  
  searchHistory.value.unshift(cleanQuery);
  if (searchHistory.value.length > 10) {
    searchHistory.value.pop();
  }
  
  localStorage.setItem('searchHistory', JSON.stringify(searchHistory.value));
};

const loadHistory = () => {
  const history = localStorage.getItem('searchHistory');
  if (history) {
    try {
      searchHistory.value = JSON.parse(history);
    } catch (e) {
      console.error('Failed to parse search history', e);
    }
  }
};

const clearHistory = () => {
  searchHistory.value = [];
  localStorage.removeItem('searchHistory');
};

const selectTag = (tag: string) => {
  searchQuery.value = tag;
  performSearch();
};

const changePage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
};

// Watchers and Lifecycle
watch(() => route.query.q, (newQ) => {
  if (newQ && typeof newQ === 'string') {
    searchQuery.value = newQ;
    if (!isLoading.value) {
        isLoading.value = true;
        setTimeout(() => isLoading.value = false, 500);
    }
  } else {
    searchQuery.value = '';
  }
}, { immediate: true });

onMounted(() => {
  loadHistory();
});
</script>

<template>
  <div class="search-page">
    <div class="search-container">
      <!-- Search Header -->
      <div class="search-header">
        <div class="search-box-wrapper">
          <div class="search-box">
            <input 
              v-model="searchQuery" 
              @keydown.enter="performSearch"
              type="text" 
              :placeholder="t('common.searchPlaceholder', 'Search articles, topics, etc...')" 
              class="search-input"
              autofocus
              aria-label="Search"
            />
            <button class="search-btn" @click="performSearch" :title="t('common.search', 'Search')" aria-label="Search">
              <span class="btn-text">Search</span>
              <IconSearch class="btn-icon" :size="20" />
            </button>
          </div>
        </div>
        
        <div class="filters-wrapper">
          <div class="filters">
            <button 
              v-for="filter in filters" 
              :key="filter" 
              class="filter-chip"
              :class="{ active: activeFilter === filter }"
              @click="activeFilter = filter"
              :aria-label="'Filter by ' + filter"
            >
              {{ filter.charAt(0).toUpperCase() + filter.slice(1) }}
            </button>
          </div>
        </div>
      </div>

      <!-- Main Content Layout (Left - Right) -->
      <div class="search-layout">
        
        <!-- Left Column: Results or History -->
        <div class="left-column">
          <transition name="fade" mode="out-in">
            <!-- Case 1: Search History (When no query) -->
            <div v-if="!searchQuery" class="history-section-wrapper">
               <div class="suggestion-section" v-if="searchHistory.length">
                <div class="section-header">
                  <h3>History</h3>
                  <button @click="clearHistory" class="clear-btn" aria-label="Clear History">Clear</button>
                </div>
                <div class="tags-cloud">
                  <button 
                    v-for="item in searchHistory" 
                    :key="item" 
                    class="tag history-tag"
                    @click="selectTag(item)"
                  >
                    <span class="history-icon">🕒</span> {{ item }}
                  </button>
                </div>
              </div>
              <div v-else class="empty-history">
                 <p>Type something to start searching...</p>
              </div>
            </div>

            <!-- Case 2: Search Results (When query exists) -->
            <div v-else class="search-results-container">
              <div v-if="isLoading" class="loading-state">
                <div class="spinner"></div>
                <p>Searching the cosmos...</p>
              </div>

              <div v-else class="results-wrapper">
                <p class="results-count">Found {{ filteredResults.length }} results</p>
                
                <div class="results-grid" v-if="paginatedResults.length">
                  <ArticleCard 
                    v-for="result in paginatedResults" 
                    :key="result.id" 
                    v-bind="result" 
                  />
                </div>
                
                <div v-else class="no-results">
                  <div class="no-results-content">
                    <IconSearch :size="48" class="no-results-icon" />
                    <h3>No matches found</h3>
                    <p>Try adjusting your search or filters to find what you're looking for.</p>
                  </div>
                </div>

                <!-- Pagination Controls -->
                <div class="pagination" v-if="totalPages > 1">
                  <button 
                    class="page-btn" 
                    :disabled="currentPage === 1" 
                    @click="changePage(currentPage - 1)"
                    aria-label="Previous Page"
                  >
                    &lt;
                  </button>
                  <span class="page-info">Page {{ currentPage }} of {{ totalPages }}</span>
                  <button 
                    class="page-btn" 
                    :disabled="currentPage === totalPages" 
                    @click="changePage(currentPage + 1)"
                    aria-label="Next Page"
                  >
                    &gt;
                  </button>
                </div>
              </div>
            </div>
          </transition>
        </div>

        <!-- Right Column: Hot Searches (Always Visible) -->
        <div class="right-column">
          <div class="suggestion-section sticky-sidebar">
            <div class="section-header">
              <h3>Hot Searches</h3>
            </div>
            <div class="tags-cloud">
              <button 
                v-for="item in hotSearches" 
                :key="item" 
                class="tag hot-tag"
                @click="selectTag(item)"
              >
                <span class="fire-icon">🔥</span> {{ item }}
              </button>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use '../styles/variables' as *;

.search-page {
  padding-top: 100px;
  padding-bottom: $spacing-xxl;
  min-height: 100vh;
  width: 100%;
  // Use theme variable for background or keep transparent if body has bg
  background: var(--color-bg-primary);
  
  @media (max-width: $breakpoint-mobile) {
    padding-top: 80px;
  }
}

.search-container {
  max-width: 1200px; // Increased width for 2 columns
  margin: 0 auto;
  padding: 0 $spacing-lg;

  @media (max-width: $breakpoint-mobile) {
    padding: 0 $spacing-md;
  }
}

.search-header {
  margin-bottom: $spacing-xl;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: $spacing-lg;

  .search-box-wrapper {
    width: 100%;
    max-width: 600px;
    position: relative;
    z-index: 10;
  }

  .search-box {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    background: var(--color-bg-secondary); // Theme aware
    backdrop-filter: blur($backdrop-blur);
    border: none;
    padding: 8px;
    border-radius: $radius-full;
    transition: $transition-base;
    box-shadow: $shadow-sm;

    &:focus-within {
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      background: var(--color-bg-primary);
    }
    
    .search-input {
      flex: 1;
      background: transparent;
      border: none;
      padding: $spacing-sm $spacing-lg;
      color: var(--color-text-primary);
      font-size: 1.1rem;
      outline: none;
      min-width: 0;
      font-family: $font-family-main;

      &::placeholder {
        color: var(--color-text-tertiary);
      }

      &:focus {
        box-shadow: none !important;
        border: none !important;
        outline: none !important;
      }
    }

    .search-btn {
      background: $gradient-primary;
      border: none;
      padding: 12px 28px;
      border-radius: $radius-full;
      color: white;
      font-weight: 600;
      cursor: pointer;
      transition: $transition-base;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
      box-shadow: 0 4px 12px rgba($color-accent-primary-rgb, 0.3);

      .btn-icon {
        display: none;
      }

      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 6px 16px rgba($color-accent-primary-rgb, 0.4);
      }

      &:active {
        transform: translateY(1px);
      }

      @media (max-width: $breakpoint-mobile) {
        padding: 12px;
        width: 48px;
        height: 48px;
        
        .btn-text {
          display: none;
        }
        
        .btn-icon {
          display: block;
        }
      }
    }
  }

  .filters-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
    
    @media (max-width: $breakpoint-mobile) {
      justify-content: flex-start;
      overflow-x: auto;
      margin: 0 -#{$spacing-md};
      padding: 0 $spacing-md $spacing-sm;
      -webkit-overflow-scrolling: touch;
      
      /* Hide scrollbar */
      scrollbar-width: none;
      &::-webkit-scrollbar {
        display: none;
      }
    }
  }

  .filters {
    display: flex;
    gap: $spacing-md;
    flex-wrap: wrap;

    @media (max-width: $breakpoint-mobile) {
      flex-wrap: nowrap;
      padding-right: $spacing-lg; // Space for end of list
    }

    .filter-chip {
      background: var(--color-bg-secondary);
      border: 1px solid var(--color-border);
      color: var(--color-text-secondary);
      padding: 8px 20px;
      border-radius: $radius-full;
      cursor: pointer;
      transition: $transition-base;
      font-size: 0.95rem;
      font-weight: 500;
      white-space: nowrap;

      &:hover {
        background: var(--color-bg-primary);
        color: var(--color-text-primary);
        border-color: var(--color-text-tertiary);
      }

      &.active {
        background: rgba($color-accent-primary-rgb, 0.1);
        border-color: var(--color-accent-primary);
        color: var(--color-accent-primary);
      }
    }
  }
}

// Layout Grid
.search-layout {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: $spacing-xl;
  
  @media (max-width: $breakpoint-desktop) {
    grid-template-columns: 1fr;
    gap: $spacing-lg;
  }
}

.left-column {
  min-width: 0; // Fix grid overflow issues
}

.right-column {
  @media (max-width: $breakpoint-desktop) {
    // On mobile/tablet, move hot searches to top or bottom? 
    // Usually sidebar goes to bottom, but for search page, maybe hot searches are important.
    // Let's keep it order-last (default)
    display: none; // Optional: Hide hot searches on mobile to reduce clutter? Or keep it.
    // Let's show it but simplified
    display: block;
  }
}

.suggestion-section {
  background: var(--color-card-bg);
  border: 1px solid var(--color-border);
  border-radius: $radius-lg;
  padding: $spacing-lg;
  backdrop-filter: blur(10px);
  
  &.sticky-sidebar {
    position: sticky;
    top: 100px; // Adjust based on navbar height
  }

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    h3 {
      font-size: 1.1rem;
      color: var(--color-text-primary);
      font-weight: 600;
    }

    .clear-btn {
      background: none;
      border: none;
      color: var(--color-text-tertiary);
      cursor: pointer;
      font-size: 0.85rem;
      transition: $transition-base;
      
      &:hover {
        color: var(--color-accent-tertiary);
        text-decoration: underline;
      }
    }
  }

  .tags-cloud {
    display: flex;
    flex-wrap: wrap;
    gap: $spacing-sm;

    .tag {
      background: var(--color-bg-secondary);
      border: 1px solid transparent;
      color: var(--color-text-secondary);
      padding: 6px 14px;
      border-radius: $radius-md;
      cursor: pointer;
      transition: $transition-base;
      font-size: 0.9rem;
      display: flex;
      align-items: center;
      gap: 6px;

      &:hover {
        background: var(--color-bg-primary);
        border-color: var(--color-border);
        color: var(--color-text-primary);
        transform: translateY(-1px);
      }

      &.hot-tag {
        // Keep hot tags distinctive but theme aware?
        // Maybe just use accent color
        background: rgba($color-accent-secondary-rgb, 0.05);
        color: var(--color-accent-secondary);
        border-color: rgba($color-accent-secondary-rgb, 0.1);

        &:hover {
          background: rgba($color-accent-secondary-rgb, 0.1);
          box-shadow: 0 2px 8px rgba($color-accent-secondary-rgb, 0.15);
        }
      }
      
      &.history-tag {
         // Subtle styling for history
         .history-icon {
            font-size: 0.8em;
            opacity: 0.7;
         }
      }
    }
  }
}

.empty-history {
    text-align: center;
    padding: $spacing-xl;
    color: var(--color-text-tertiary);
}

.results-wrapper {
  .results-count {
    margin-bottom: $spacing-lg;
    color: var(--color-text-secondary);
    font-family: $font-family-code;
    font-size: 0.9rem;
  }

  .results-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: $spacing-lg;
    margin-bottom: $spacing-xl;
  }
}

.no-results {
  display: flex;
  justify-content: center;
  padding: $spacing-xxl 0;

  .no-results-content {
    text-align: center;
    color: var(--color-text-secondary);
    max-width: 400px;

    .no-results-icon {
      margin-bottom: $spacing-lg;
      opacity: 0.5;
      color: var(--color-text-tertiary);
    }

    h3 {
      font-size: 1.5rem;
      color: var(--color-text-primary);
      margin-bottom: $spacing-sm;
    }

    p {
      line-height: 1.6;
    }
  }
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: $spacing-xxl;
  gap: $spacing-lg;
  color: var(--color-text-secondary);

  .spinner {
    width: 48px;
    height: 48px;
    border: 3px solid rgba($color-accent-primary-rgb, 0.1);
    border-radius: 50%;
    border-top-color: var(--color-accent-primary);
    animation: spin 1s cubic-bezier(0.6, 0.2, 0.4, 0.8) infinite;
    box-shadow: 0 0 20px rgba($color-accent-primary-rgb, 0.2);
  }
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: $spacing-lg;
  padding-top: $spacing-xl;
  border-top: 1px solid var(--color-border);

  .page-btn {
    background: var(--color-bg-secondary);
    border: 1px solid var(--color-border);
    color: var(--color-text-primary);
    width: 40px;
    height: 40px;
    border-radius: $radius-full;
    cursor: pointer;
    transition: $transition-base;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;

    &:hover:not(:disabled) {
      background: rgba($color-accent-primary-rgb, 0.1);
      border-color: var(--color-accent-primary);
      color: var(--color-accent-primary);
    }

    &:disabled {
      opacity: 0.3;
      cursor: not-allowed;
      background: transparent;
    }
  }

  .page-info {
    color: var(--color-text-secondary);
    font-family: $font-family-code;
    font-size: 0.9rem;
  }
}

// Animations
@keyframes spin {
  to { transform: rotate(360deg); }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
